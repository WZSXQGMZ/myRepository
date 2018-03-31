using System;

using System.Windows.Forms;
using System.Drawing;
using System.IO;
using System.Threading;

namespace DrawWave
{
    /*此类用于放置图表
     */
    public class PanelPictureBox : Panel
    {
        //设置程序能读的最大文件长度，1MB
        private const long maxFileSize = 1 << 20;
        //存放数据的short数组
        private short[] data = null;
        //存放data数组的大小
        private int dataSize = 0;
        //存放每单个数据的显示间隔
        private int interval = 2;
        //存放文件路径
        private string filePath = null;
        //文件经过的操作
        private string oprate_str = "";
        //文件另存为的路径
        private string save_filePath = null;
        //原始数据的缩放倍数
        private int y_data_scale = 1;
        //是否创建成功
        private bool created = true;

        //放置图表的panel_chart
        PanelChart panel_chart = null;

        //放置button的panel
        Panel panel_button = null;
        //放大缩小的按钮
        Button button_zoomin = null;
        Button button_zoomout = null;
        //sin计算按钮
        Button button_math_sin = null;
        //cos计算按钮
        Button button_math_cos = null;
        //关闭按钮
        Button button_close = null;
        //保存按钮
        Button button_save = null;
        //保存图像按钮
        Button button_save_bitmap = null;

        //放置textBox_filePath的panel
        Panel panel_textBox_filePath = null;
        //文件路径文本框
        TextBox textBox_filePath = null;
        //文件路径label
        //Label label_filePath = null;

        /*构造函数
         * path: data数组的文件路径
         */
        public PanelPictureBox(string path)
        {
            //设置该控件的各种属性
            this.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
            this.AutoScroll = true;
            this.Dock = DockStyle.Top;
            this.Height = 400;

            filePath = path;

            panel_chart_initialize();
            panel_button_initialize();
            panel_textBox_filePath_initialize(path);
            drawWaveFunc(path);
        }
        /*重载构造函数，此函数用于不读取文件而是直接传入short数组来构造此类
         * data: 传入的short类型数组
         * oprate_str: data数组已经过的操作
         * y_data_scale: data数组被放大的倍率
         */
        public PanelPictureBox(string path,short[] data,string oprate_str, int y_data_scale)
        {
            //设置该控件的各种属性
            this.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
            this.AutoScroll = true;
            this.Dock = DockStyle.Top;
            this.Height = 400;

            this.data = data;
            dataSize = data.Length - 2;
            filePath = path;
            this.y_data_scale = y_data_scale;

            panel_chart_initialize();
            panel_button_initialize();
            //通过此构造函数多添加一个保存按钮
            button_save = new Button();
            button_save.Text = "保存";
            button_save.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
            button_save.Click += button_save_Click;
            button_save.Location = new Point(button_save_bitmap.Location.X + 100, button_save_bitmap.Location.Y);
            panel_button.Controls.Add(button_save);

            panel_textBox_filePath_initialize(path);
            drawWaveFunc(path,data,oprate_str);
        }

        /*初始化panel_button的函数
         */
        private void panel_button_initialize()
        {
            //设置按钮panel
            panel_button = new Panel();
            panel_button.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
            panel_button.Dock = DockStyle.Top;
            panel_button.Height = 30;
            this.Controls.Add(panel_button);
            //设置缩放按钮
            button_zoomin = new Button();
            button_zoomin.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
            button_zoomin.Text = "放大";
            button_zoomin.Click += button_zoomin_Click;
            panel_button.Controls.Add(button_zoomin);
            button_zoomout = new Button();
            button_zoomout.Text = "缩小";
            button_zoomout.Click += button_zoomout_Click;
            panel_button.Controls.Add(button_zoomout);
            button_zoomout.Location = new Point(button_zoomin.Location.X + 100, button_zoomin.Location.Y);
            //设置关闭按钮
            button_close = new Button();
            button_close.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
            button_close.Text = "关闭";
            button_close.Click += button_close_Click;
            button_close.Location = new Point(button_zoomout.Location.X + 100, button_zoomout.Location.Y);
            panel_button.Controls.Add(button_close);
            //设置sin计算按钮
            button_math_sin = new Button();
            button_math_sin.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
            button_math_sin.Text = "Sin";
            button_math_sin.Click += button_math_sin_Click;
            button_math_sin.Location = new Point(button_close.Location.X + 200, button_close.Location.Y);
            panel_button.Controls.Add(button_math_sin);
            //设置cos计算按钮
            button_math_cos = new Button();
            button_math_cos.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
            button_math_cos.Text = "Cos";
            button_math_cos.Click += button_math_cos_Click;
            button_math_cos.Location = new Point(button_math_sin.Location.X + 100, button_math_sin.Location.Y);
            panel_button.Controls.Add(button_math_cos);
            //设置保存图像按钮
            button_save_bitmap = new Button();
            button_save_bitmap.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
            button_save_bitmap.Text = "保存图像";
            button_save_bitmap.Click += button_save_bitmap_Click;
            button_save_bitmap.Location = new Point(button_math_cos.Location.X + 100, button_math_cos.Location.Y);
            panel_button.Controls.Add(button_save_bitmap);

        }

        /*初始化panel_chart的函数
         */
        private void panel_chart_initialize()
        {
            //设置panel_chart
            panel_chart = new PanelChart();
            panel_chart.Dock = DockStyle.Top;
            panel_chart.Height = this.Height - 60;
            this.Controls.Add(panel_chart);

        }

        /*初始化panel_textBox_filePath的函数
         */
        private void panel_textBox_filePath_initialize(string path)
        {
            //设置文本的panel
            panel_textBox_filePath = new Panel();
            panel_textBox_filePath.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
            panel_textBox_filePath.Dock = DockStyle.Top;
            panel_textBox_filePath.Height = 30;
            //panel_textBox.BackColor = Color.White;
            this.Controls.Add(panel_textBox_filePath);

            //设置文件路径textBox
            textBox_filePath = new TextBox();
            textBox_filePath.Anchor = (AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right);
            textBox_filePath.Dock = DockStyle.Fill;
            textBox_filePath.Text = "Loading";
            textBox_filePath.ReadOnly = true;
            textBox_filePath.BackColor = Color.White;
            //textBox_filePath.Location = new Point(button_close.Location.X + 100, button_close.Location.Y);
            panel_textBox_filePath.Controls.Add(textBox_filePath);

        }

        //析构函数
        ~PanelPictureBox()
        {
            myDispose();
        }

        /*此函数用于释放该类占用的资源
         */
        public void myDispose()
        {
            try
            {
                data = null;
                //释放button
                button_zoomin.Dispose();
                button_zoomout.Dispose();
                button_close.Dispose();
                button_save_bitmap.Dispose();
                if(button_save != null)
                {
                    button_save.Dispose();
                }
                //释放textBox
                textBox_filePath.Dispose();
                //释放panel
                panel_textBox_filePath.Dispose();
                panel_button.Dispose();
                if(panel_chart != null)
                    panel_chart.myDispose();
                this.Dispose();
            }
            catch
            {
                MessageBox.Show("关闭失败");
            }
        }

        /*此函数用于外部判断该类是否创建成功
         */
        public bool Created()
        {
            return created;
        }

        /*此函数用于调用构造函数时读取数据、绘制表格
         * path: 文件路径
         * return: true为绘制成功，false为绘制失败
         */
        private bool drawWaveFunc(string path)
        {
            //读取数据
            if (!read_double_byte(path))
            {
                created = false;
                return false;
            }
            //绘制表格
            panel_chart.reDraw(data, interval);

            textBox_filePath.Text = path;

            return true;
        }
        /*重载drawWaveFunc，直接传入data，不读取文件
         * path: 源文件的路径
         * data: 要绘制的数据
         * oprate: 已进行的操作
         * return: true为绘制成功，false为绘制失败
         */
        private bool drawWaveFunc(string path, short[]data, String oprate_str)
        {
            if(data ==null || path == null || oprate_str == null)
            {
                created = false;
                return false;
            }
            //绘制表格
            panel_chart.reDraw(data, interval,y_data_scale);
            
            if(oprate_str != "")
            {
                this.oprate_str += oprate_str;
            }
            textBox_filePath.Text = path + " " + this.oprate_str;

            return true;
        }

        /*此函数用于读取二进制文件，每两个byte组成一个short型数据
         * path: 需要读取的文件路径
         * return: true为读取正常，false为读取出错
         */
        private bool read_double_byte(string path)
        {
            long fileLength = 0;    //文件长度
            FileStream fs = null;   //文件流
            int fileLength_int = 0; //int型文件长度
            try
            {
                //打开文件
                fs = new FileStream(path, FileMode.Open, FileAccess.Read);
                //如果fs为空，则返回false
                if (fs == null)
                {
                    return false;
                }
                fileLength = fs.Length;
                //如果文件长度过长或过短，则返回false
                if (maxFileSize < fileLength)
                {
                    MessageBox.Show("文件过大");
                    return false;
                }
                //将文件长度转为int型
                fileLength_int = Convert.ToInt32(fileLength);
                //计算data数组大小，fileLength的一半
                dataSize = fileLength_int / 2 + fileLength_int % 2;
                //申请int数组存放文件内容,最后两位存放最小值和最大值
                data = new short[dataSize + 2];
                //读取文件
                byte[] temp = new byte[1];
                byte[] temp_1 = new byte[1];
                //short temp_short = 0;

                int boundary = fileLength_int - 1;
                short maxData = -32768;
                short minData = 32767;
                short temp_short = 0;
                int n = fileLength_int % 2;
                long m = fileLength - n;
                int i = 0, j = 0;
                for (i = 0, j = 0; i < m; j++)
                {
                    fs.Read(temp, 0, 1);
                    i++;
                    fs.Read(temp_1, 0, 1);
                    i++;

                    temp_short = (short)((temp[0] << 8) | temp_1[0]);
                    data[j] = temp_short;
                    //更新最大最小值
                    if (temp_short > maxData)
                    {
                        maxData = temp_short;
                    }
                    else if (temp_short < minData)
                    {
                        minData = temp_short;
                    }
                }
                //如果文件末尾不能凑成两个byte
                if (n == 1)
                {
                    fs.Read(temp, 0, 1);
                    data[j] = Convert.ToInt16(temp[0]);
                }
                data[dataSize] = minData;
                data[dataSize + 1] = maxData;
                temp = null;
                temp_1 = null;
                //关闭文件
                fs.Flush();
                fs.Close();
            }
            catch (Exception e)
            {
                MessageBox.Show("读取文件失败");

            }
            return true;
        }

        //用按钮方式放大
        private void button_zoomin_Click(object sender, EventArgs e)
        {
            //判断data和interval是否正确
            if(data == null)
            {
                return;
            }
            if (interval >= 5)
            {
                return;
            }
            else if(interval == -2)
            {
                interval += 3;
            }else
            {
                interval++;
            }
            //改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { }
            //重新绘制图表
            panel_chart.reDraw(data, interval, y_data_scale);
            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { }
        }
        //用按钮方式缩小
        private void button_zoomout_Click(object sender, EventArgs e)
        {
            //判断data和interval是否正确
            if (data == null)
            {
                return;
            }
            if (interval <= -5)
            {
                return;
            }
            else if (interval == 1)
            {
                interval -= 3;
            }
            else
            {
                interval--;
            }
            //改变鼠标为等待样式
            try{ Cursor = Cursors.WaitCursor; }catch { }
            //重新绘制图表
            panel_chart.reDraw(data, interval, y_data_scale);
            //改变鼠标为默认样式
            try{ Cursor = Cursors.Default; }catch { }
        }

        //执行sin计算的按钮
        private void button_math_sin_Click(object sender, EventArgs e)
        {
            //改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { }

            short[] data_oprated = new short[dataSize + 2];
            //执行操作
            if (oprate_sin(data, data_oprated) == null)
            {
                data_oprated = null;
                return;
            }
            //获取父容器
            Panel parentPanel = this.Parent as Panel;
            //在父容器中添加新的PanelPictureBox
            parentPanel.Controls.Add(new PanelPictureBox(filePath, data_oprated, oprate_str + "-sin", 10000));

            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { }
        }
        //执行cos计算的按钮
        private void button_math_cos_Click(object sender, EventArgs e)
        {
            //改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { }

            short[] data_oprated = new short[dataSize + 2];
            //执行操作
            if (oprate_cos(data, data_oprated) == null)
            {
                data_oprated = null;
            }
            //获取父容器
            Panel parentPanel = this.Parent as Panel;
            //在父容器中添加新的PanelPictureBox
            parentPanel.Controls.Add(new PanelPictureBox(filePath, data_oprated, oprate_str + "-cos", 10000));

            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { }
        }


        //执行保存的按钮
        private void button_save_Click(object sender, EventArgs e)
        {
            //新建一个线程，否则直接打开OpenFileDialog会出错
            Thread td = new Thread(new ParameterizedThreadStart(callSaveFileDialog));
            td.SetApartmentState(ApartmentState.STA);
            //传入保存的文件类型参数
            td.Start("所有文件(*.*)|*.*");
            while (td.IsAlive)
            {
                Application.DoEvents();
            }
            if(save_filePath == null || save_filePath == "")
            {
                return;
            }

            // 改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { }
            //保存文件
            bool flag = saveFile(save_filePath, data);
            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { }

            if (flag)
            {
                MessageBox.Show("保存成功");
            }else
            {
                MessageBox.Show("保存失败");
            }
            save_filePath = null;
        }
        //执行保存图像的按钮
        private void button_save_bitmap_Click(object sender, EventArgs e)
        {
            //新建一个线程，否则直接打开OpenFileDialog会出错
            Thread td = new Thread(new ParameterizedThreadStart(callSaveFileDialog));
            td.SetApartmentState(ApartmentState.STA);
            //传入保存的文件类型参数
            td.Start("png图像(*.png)|*.png");
            while (td.IsAlive)
            {
                Application.DoEvents();
            }
            if (save_filePath == null || save_filePath == "")
            {
                return;
            }

            // 改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { }
            //保存文件
            bool flag = panel_chart.saveBitmap(save_filePath);
            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { }

            if (flag)
            {
                MessageBox.Show("保存成功");
            }
            else
            {
                MessageBox.Show("保存失败");
            }
            save_filePath = null;
        }

        //sin操作
        private short[] oprate_sin (short[] data_old,short[] data_new)
        {
            //判断数组不为空
            if(data_old == null || data_new == null)
            {
                return null;
            }
            //判断数组大小相同
            int length = data_old.Length;
            if(length != data_new.Length)
            {
                return null;
            }
            int size = length - 2;
            double temp_double = 0;
            short max = -32768;
            short min = 32767;
            //进行操作
            for(int i = 0; i < size; i++)
            {
                temp_double = System.Math.Sin(Convert.ToDouble(data_old[i]) / y_data_scale);
                data_new[i] = Convert.ToInt16(temp_double * 10000);
                //计算最大最小值
                if (data_new[i] > max)
                {
                    max = data_new[i];
                }else if(data_new[i] < min)
                {
                    min = data_new[i];
                }
            }
            //放入最大最小值
            data_new[size] = min;
            data_new[size + 1] = max;

            return data_new;
        }
        //cos操作
        private short[] oprate_cos(short[] data_old, short[] data_new)
        {
            //判断数组不为空
            if (data_old == null || data_new == null)
            {
                return null;
            }
            //判断数组大小相同
            int length = data_old.Length;
            if (length != data_new.Length)
            {
                return null;
            }
            int size = length - 2;
            double temp_double = 0;
            short max = -32768;
            short min = 32767;
            //进行操作
            for (int i = 0; i < size; i++)
            {
                temp_double = System.Math.Cos(Convert.ToDouble(data_old[i]) / y_data_scale);
                data_new[i] = Convert.ToInt16(temp_double * 10000);
                //计算最大最小值
                if (data_new[i] > max)
                {
                    max = data_new[i];
                }
                else if (data_new[i] < min)
                {
                    min = data_new[i];
                }
            }
            //放入最大最小值
            data_new[size] = min;
            data_new[size + 1] = max;

            return data_new;
        }

        //用鼠标滚轮方式缩放，暂不使用
        private void mouse_wheel_roll(object sender, MouseEventArgs e)
        {
            //如果panel_chart未实例化则不进行操作
            if (panel_chart == null)
            {
                return;
            }
            try
            {
                //如果向上滚
                if (e.Delta > 0)
                {
                    if (interval >= 30)
                    {
                        return;
                    }
                    interval++;
                    panel_chart.reDraw(data, interval, y_data_scale);
                }
                //如果向下滚
                else
                {
                    if (interval <= 1)
                    {
                        return;
                    }
                    interval--;
                    panel_chart.reDraw(data, interval, y_data_scale);
                }
            }
            catch
            {
                return;
            }
        }

        //关闭按钮
        private void button_close_Click(object sender, EventArgs e)
        {
            //改变鼠标为等待样式
            try
            {
                Cursor = Cursors.WaitCursor;
            }
            catch { }
            //释放资源
            myDispose();
            //改变鼠标为默认样式
            try
            {
                Cursor = Cursors.Default;
            }
            catch { }
        }

        /*打开文件对话框的函数
         * obj: 默认为String类型，为要保存的文件后缀名
         */
        private void callSaveFileDialog(object obj)
        {
            SaveFileDialog saveFileDialog = new SaveFileDialog();
            //设置初始目录与原文件相同
            saveFileDialog.InitialDirectory = Path.GetDirectoryName(filePath);
            //将默认文件类型改为传入参数obj所带的类型
            String fileType = obj.ToString();
            saveFileDialog.Filter = obj.ToString();
            saveFileDialog.RestoreDirectory = true;
            saveFileDialog.Title = "保存文件";
            //如果保存类型为所有，则自动添加后缀名.dat
            if (fileType.Equals("所有文件(*.*)|*.*"))
            {
                saveFileDialog.FileName = Path.GetFileNameWithoutExtension(filePath) + oprate_str + ".dat";
            }
            //否则不加后缀名
            else
            {
                saveFileDialog.FileName = Path.GetFileNameWithoutExtension(filePath) + oprate_str;
            }
            while (true)
            {
                //如果选定文件
                if (saveFileDialog.ShowDialog() == DialogResult.OK)
                {
                    save_filePath = saveFileDialog.FileName;
                    //如果文件已存在
                    if (File.Exists(save_filePath))
                    {
                        //消息框中需要显示哪些按钮，此处显示“确定”和“取消”
                        MessageBoxButtons messButton = MessageBoxButtons.OKCancel;
                        DialogResult dr = MessageBox.Show("文件 " + Path.GetFileName(save_filePath) + " 已存在，要覆盖吗？", "文件已存在", messButton);
                        if (dr == DialogResult.OK)//如果点击“确定”按钮
                        {
                            //直接退出循环
                            break;
                        }
                        else//如果点击“取消”按钮
                        {
                            saveFileDialog.FileName = "";
                        }
                    }
                    //如果文件名为空
                    else if (saveFileDialog.FileName == null || saveFileDialog.FileName == "")
                    {
                        MessageBox.Show("请输入文件名");
                    }
                    //如果文件名不存在也不为空
                    else
                    {
                        //直接退出循环
                        break;
                    }
                }
                //如果点击取消
                else
                {
                    break;
                }
            }
        }

        /*此函数用于保存文件
         * save_path: 要保存的文件路径
         * save_data: 要保存的数据
         * return: true为保存成功，false为保存出错
         */
        private bool saveFile(string save_path, short[] save_data)
        {
            if(save_data == null || save_data.Length == 0 || save_path == null)
            {
                return false;
            }
            FileStream fs = null;
            int length = save_data.Length - 2;  //最后两位是最大最小值
            try
            {
                byte[] temp_data = new byte[2];
                fs = new FileStream(save_path, FileMode.Create);
                for(int i = 0; i < length; i++)
                {
                    //将short的前8位和后8为分别放入temp_data的0和1号位
                    temp_data[0] = (byte)(save_data[i] >> 8);
                    temp_data[1] = (byte)(save_data[i]);
                    //写入文件
                    fs.Write(temp_data, 0, 2);
                }

                //关闭fs
                fs.Flush();
                fs.Close();
            }
            catch
            {
                return false;
            }

            return true;
        }
    }
}
