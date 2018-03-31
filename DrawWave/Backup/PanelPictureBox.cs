using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection.Emit;
using System.Text;

using System.Windows.Forms;
using System.Drawing;
using System.IO;

namespace DrawWave
{
    /*此类用于放置图表
     */
    public class PanelPictureBox : Panel
    {
        //设置程序能读的最大文件长度，0.5GB
        private const long maxFileSize = 1 << 8;
        //存放数据的short数组
        private short[] data = null;
        //存放data数组的大小
        private int dataSize = 0;
        //存放每单个数据的显示间隔
        private int interval = 20;

        //放置panel_pictureBox和panel_axis的panel
        Panel panel_chart = null;
        //放置pictureBox的panel
        Panel panel_picture = null;
        //显示图表的pictureBox
        PictureBox pictureBox = null;
        //存放绘图的bitmap
        Bitmap bitmap = null;
        //放置坐标值的panel
        Panel panel_axis = null;
        //显示坐标的pictureBox
        PictureBox pictureBox_axis = null;
        //存放坐标的bitmap
        Bitmap bitmap_axis = null;

        //放置button的panel
        Panel panel_button = null;
        //放大缩小的按钮
        Button button_zoomin = null;
        Button button_zoomout = null;
        //关闭按钮
        Button button_close = null;

        //文件路径文本框
        TextBox textBox_filePath = null;
        //放置textBox的panel
        Panel panel_textBox = null;

        //构造函数
        public PanelPictureBox(String path)
        {
            //设置该控件的各种属性
            this.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
            this.AutoScroll = true;
            this.Dock = DockStyle.Top;
            this.Height = 400;

            panel_chart_initialize(path);
            panel_button_initialize();
            panel_textBox_initialize(path);
            
        }

        //初始化panel_button的函数
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
        }

        //初始化panel_chart的函数
        private void panel_chart_initialize(String path)
        {
            //设置panel_chart
            panel_chart = new Panel();
            panel_chart.Dock = DockStyle.Top;
            panel_chart.Height = this.Height - 60;
            this.Controls.Add(panel_chart);

            //设置panel_pictureBox
            panel_picture = new Panel();
            panel_picture.Anchor = (AnchorStyles.Top);
            panel_picture.Dock = DockStyle.Top;
            panel_picture.AutoScroll = true;
            panel_picture.Height = panel_chart.Height;
            panel_chart.Controls.Add(panel_picture);
            //设置pictureBox
            pictureBox = new PictureBox();
            panel_picture.Controls.Add(pictureBox);
            pictureBox.Location = new Point(0, 0);
            pictureBox.Width = panel_picture.Width;
            pictureBox.Height = panel_picture.Height;
            pictureBox.SizeMode = PictureBoxSizeMode.AutoSize;
            pictureBox.Dock = DockStyle.None;

            //设置panel_axis
            panel_axis = new Panel();
            panel_axis.Dock = DockStyle.Left;
            panel_axis.Height = panel_chart.Height;
            panel_axis.Width = 50;
            panel_chart.Controls.Add(panel_axis);
            //设置pictureBox_axis
            pictureBox_axis = new PictureBox();
            panel_axis.Controls.Add(pictureBox_axis);
            pictureBox_axis.Dock = DockStyle.Fill;

            //绘制表格
            drawWaveFunc(path);            
        }

        //初始化panel_textBox的函数
        private void panel_textBox_initialize(String path)
        {
            //设置文本框的panel
            panel_textBox = new Panel();
            panel_textBox.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
            panel_textBox.Dock = DockStyle.Top;
            panel_textBox.Height = 30;
            this.Controls.Add(panel_textBox);

            //设置文件路径文本框
            textBox_filePath = new TextBox();
            textBox_filePath.Anchor = (AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right);
            textBox_filePath.Dock = DockStyle.Fill;
            textBox_filePath.Text = path;
            textBox_filePath.ReadOnly = true;
            textBox_filePath.Location = new Point(button_close.Location.X + 100, button_close.Location.Y);
            panel_textBox.Controls.Add(textBox_filePath);

        }

        ~PanelPictureBox()
        {
            myDispose();
        }

        public void myDispose()
        {
            try
            {
                bitmap.Dispose();
                bitmap_axis.Dispose();
                data = null;
                pictureBox.Dispose();
                button_zoomin.Dispose();
                button_zoomout.Dispose();
                button_close.Dispose();
                textBox_filePath.Dispose();
                panel_button.Dispose();
                panel_picture.Dispose();
                panel_axis.Dispose();
                panel_chart.Dispose();
                this.Dispose();
            }
            catch
            {
                MessageBox.Show("关闭失败");
            }
        }

        /*此函数用于调用构造函数时读取数据、绘制表格
         * path: 文件路径
         * return: true为绘制成功，false为绘制失败
         */
        private bool drawWaveFunc(String path)
        {
            //读取数据
            read_double_byte(path);
            //绘制表格
            drawWave(data, interval);

            return true;
        }

        /*此函数用于读取二进制文件，每两个byte组成一个short型数据
         * path: 需要读取的文件路径
         * return: true为读取正常，false为读取出错
         */
        private bool read_double_byte(String path)
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
                if (maxFileSize < fileLength || fileLength < 2)
                {
                    return false;
                }
                //将文件长度转为int型
                fileLength_int = Convert.ToInt32(fileLength);
                //计算data数组大小
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
                for (int i = 0, j = 0; i < fileLength; j++)
                {
                    fs.Read(temp, 0, 1);
                    i++;
                    //如果文件末尾不能凑成两个byte
                    if ((i == boundary) && ((fileLength_int % 2) == 1))
                    {
                        data[j] = Convert.ToInt16(temp[0]);
                    }
                    fs.Read(temp_1, 0, 1);
                    i++;

                    temp_short = (short)((temp[0] << 8) | temp_1[0]);
                    data[j] = temp_short;
                    //更新最大最小值
                    if (data[j] > maxData)
                    {
                        maxData = data[j];
                    }
                    else if (data[j] < minData)
                    {
                        minData = data[j];
                    }
                }
                data[dataSize] = minData;
                data[dataSize + 1] = maxData;
                temp = null;
                temp_1 = null;
                //关闭文件
                fs.Close();
            }
            catch (Exception e)
            {
                MessageBox.Show("读取文件失败");

            }
            return true;
        }

        /*此函数用于绘制bitmap
         * data:读取的数据
         * newInterval:需要设置的数据显示间隔
         */
        private void drawWave(short[] data, int newInterval)
        {
            //如果数据为空则返回
            if (data == null || newInterval <= 0 || newInterval > 30)
            {
                return;
            }
            //表格向右移若干像素点，留空间写坐标值
            int x_offset = 0;
            //获取data大小
            int dataLength = dataSize;
            //获取pictureBox大小
            int picBoxWidth = newInterval * dataLength;
            int picBoxHeight = (panel_picture.Height - 20);
            int halfPicBoxHeight = picBoxHeight / 2;
            //生成位图
            if (bitmap != null)
            {
                bitmap.Dispose();
            }
            if(bitmap_axis != null)
            {
                bitmap_axis.Dispose();
            }

            bitmap = new Bitmap(picBoxWidth, picBoxHeight);
            bitmap_axis = new Bitmap(pictureBox_axis.Width, pictureBox_axis.Height);
            Graphics g = Graphics.FromImage(bitmap);
            Graphics g_axis = Graphics.FromImage(bitmap_axis);
            g.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;
            //各种画线
            //绘制表格
            //y轴
            g.DrawLine(new Pen(Color.Black, 1), new Point(x_offset, picBoxHeight), new Point(x_offset, 0));
            //x轴
            g.DrawLine(new Pen(Color.Black, 2), new Point(x_offset, halfPicBoxHeight), new Point(picBoxWidth, halfPicBoxHeight));
            Pen pen = new Pen(Color.Red, 1);
            Pen pen_black = new Pen(Color.Black, 0.5f);
            Pen pen_dash = new Pen(Color.Black, 0.5f);
            pen_dash.DashStyle = System.Drawing.Drawing2D.DashStyle.Custom;
            pen_dash.DashPattern = new float[] { 1f, 5f };

            int boundary = dataSize - 1;

            //计算y轴缩放倍率
            int max = 0;
            short data_max1 = System.Math.Abs(data[dataSize + 1]);
            short data_max2 = System.Math.Abs(data[dataSize]);
            max = Convert.ToInt32(data_max1 > data_max2 ? data_max1 : data_max2);

            int y_scale = (max / halfPicBoxHeight) + 1;
            int x = -newInterval + x_offset;
            int y = 0;
            int next_x = 0;
            int next_y = 0;
            for (int i = 0; i < boundary; i++)
            {
                //计算连续两点的位置
                x += newInterval;
                y = halfPicBoxHeight - data[i] / y_scale;
                next_x = x + newInterval;
                next_y = halfPicBoxHeight - data[i + 1] / y_scale;
                //绘制折线
                g.DrawLine(pen, new Point(x, y), new Point(next_x, next_y));
                //绘制纵向虚线
                g.DrawLine(pen_dash, new Point(x, 0), new Point(x, picBoxHeight));
            }
            g.DrawLine(pen_dash, new Point(x + newInterval, 0), new Point(x + newInterval, picBoxHeight));

            //计算横向实线的间隔，间隔的差值为500的倍数，使得横线条数约为6条
            int y_interval = halfPicBoxHeight * y_scale;
            y_interval /= 6;
            y_interval /= 500;
            y_interval *= 500;
            //横向虚线的条数
            int y_count = halfPicBoxHeight / (y_interval / y_scale);
            //横向虚线的坐标值
            int y_line = 0;
            //横向虚线的累加像素
            int y_line_pix = 0;
            //横向虚线的真实y轴坐标
            int y_line_pix_real = 0;
            //设置坐标值的字体
            Font font = new Font("宋体", 10, FontStyle.Bold);
            for (int i = 0; i < y_count; i++)
            {
                y_line += y_interval;
                y_line_pix = y_line / y_scale;
                y_line_pix_real = halfPicBoxHeight - y_line_pix;
                //绘制横向实线
                g.DrawLine(pen_black, new Point(x_offset, y_line_pix_real), new Point(picBoxWidth, y_line_pix_real));
                //绘制坐标值
                g_axis.DrawString(y_line.ToString(), font, Brushes.DimGray, new Point(0, y_line_pix_real));
            }
            y_line = 0;
            for (int i = 0; i < y_count; i++)
            {
                y_line -= y_interval;
                y_line_pix = y_line / y_scale;
                y_line_pix_real = halfPicBoxHeight - y_line_pix;
                //绘制横向实线
                g.DrawLine(pen_black, new Point(x_offset, y_line_pix_real), new Point(picBoxWidth, y_line_pix_real));
                //绘制坐标值
                g_axis.DrawString(y_line.ToString(), font, Brushes.DimGray, new Point(0, y_line_pix_real));
            }
            //零点坐标值
            g_axis.DrawString(0.ToString(), font, Brushes.DimGray, new Point(0, halfPicBoxHeight));
            g.DrawLine(pen_dash, new Point(x_offset, 0), new Point(picBoxWidth, 0));
            g.DrawLine(pen_dash, new Point(x_offset, picBoxHeight), new Point(picBoxWidth, picBoxHeight));

            pictureBox.Image = bitmap;
            pictureBox_axis.Image = bitmap_axis;
            g.Dispose();
            g_axis.Dispose();
            interval = newInterval;
        }

        //用按钮的方式缩放
        private void button_zoomin_Click(object sender, EventArgs e)
        {
            if (interval >= 30 || data == null)
            {
                return;
            }
            interval++;
            drawWave(data, interval);
        }

        private void button_zoomout_Click(object sender, EventArgs e)
        {
            if (interval <= 1 || data == null)
            {
                return;
            }
            interval--;
            drawWave(data, interval);
        }

        //用鼠标滚轮方式缩放
        private void mouse_wheel_roll(object sender, MouseEventArgs e)
        {
            //如果bitmap未实例化则不进行操作
            if (bitmap == null)
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
                    drawWave(data, interval);
                }
                //如果向下滚
                else
                {
                    if (interval <= 1)
                    {
                        return;
                    }
                    interval--;
                    drawWave(data, interval);
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
            myDispose();
        }
    }
}
