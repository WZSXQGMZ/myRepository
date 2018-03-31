using System;

using System.Windows.Forms;
using System.Drawing;
using System.IO;

namespace DrawWave
{
    /*此类用于绘制图表和显示图表
     */
    class PanelChart : Panel
    {
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

        //构造函数
        public PanelChart()
        {
            panel_chart_initialize();
        }

        /*此函数用于释放该类占用的资源
         */
        public void myDispose()
        {
            try
            {
                if(bitmap_axis != null)
                    bitmap_axis.Dispose();
                if (bitmap != null)
                    bitmap.Dispose();
                if (pictureBox_axis != null)
                    pictureBox_axis.Dispose();
                if (pictureBox != null)
                    pictureBox.Dispose();
                if (panel_axis != null)
                    panel_axis.Dispose();
                if (panel_picture != null)
                    panel_picture.Dispose();
                this.Dispose();
            }
            catch
            {
                MessageBox.Show("关闭失败");
            }
        }

        /*初始化panel_chart的函数
         */
        private void panel_chart_initialize()
        {
            //设置panel_chart
            this.Dock = DockStyle.Top;

            //设置panel_pictureBox
            panel_picture = new Panel();
            panel_picture.Anchor = (AnchorStyles.Top);
            panel_picture.Dock = DockStyle.Top;
            panel_picture.AutoScroll = true;
            panel_picture.Height = this.Height;
            this.Controls.Add(panel_picture);
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
            panel_axis.Height = this.Height;
            panel_axis.Width = 50;
            this.Controls.Add(panel_axis);
            //设置pictureBox_axis
            pictureBox_axis = new PictureBox();
            panel_axis.Controls.Add(pictureBox_axis);
            pictureBox_axis.Dock = DockStyle.Fill;

        }

        /*此函数用于绘制bitmap
         * data:读取的数据
         * newInterval:需要设置的数据显示间隔
         * y_data_scale:表示原数据被放大了多少倍
         */
        private void drawWave(short[] data, int newInterval, int y_data_scale)
        {
            //如果数据为空则返回
            if (data == null || newInterval <= 0 || newInterval > 5 || this.Height < 10 || y_data_scale < 1)
            {
                return;
            }
            //使两个容器的高度与父panel相同
            panel_picture.Height = this.Height;
            panel_axis.Height = this.Height;
            int dataSize = data.Length - 2;
            //表格向右移若干像素点，留空间写坐标值
            int x_offset = 0;
            //获取data大小
            int dataLength = dataSize;
            //计算pictureBox大小
            int picBoxWidth = newInterval * dataLength;
            int picBoxHeight = (panel_picture.Height - 40);
            int halfPicBoxHeight = picBoxHeight / 2;
            //生成位图
            if (bitmap != null)
            {
                bitmap.Dispose();
            }
            if (bitmap_axis != null)
            {
                bitmap_axis.Dispose();
            }
            //新建位图
            bitmap = new Bitmap(picBoxWidth, picBoxHeight + 20);
            bitmap_axis = new Bitmap(pictureBox_axis.Width, bitmap.Height);
            Graphics g = Graphics.FromImage(bitmap);
            Graphics g_axis = Graphics.FromImage(bitmap_axis);
            g.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;
            //各种画线
            //绘制表格
            //设置背景为白色
            g.Clear(Color.White);
            g_axis.Clear(Color.White);
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
            short data_max1 = 0;
            short data_max2 = 0;
            //如果最大、最小值是-32768，不进行取绝对值，否则会报错
            if (data[dataSize] == -32768 || data[dataSize + 1] == -32768)
            {
                max = 32768;
            }else
            {
                data_max1 = System.Math.Abs(data[dataSize + 1]);
                data_max2 = System.Math.Abs(data[dataSize]);
                max = Convert.ToInt32(data_max1 > data_max2 ? data_max1 : data_max2);
            }

            int y_scale = (max / halfPicBoxHeight) + 1;
            int x = -newInterval + x_offset;
            int y = 0;
            int next_x = 0;
            int next_y = 0;
            //绘制折线
            x += newInterval;
            y = halfPicBoxHeight - data[0] / y_scale;
            for (int i = 0; i < boundary; i++)
            {
                //计算连续两点的位置
                next_x = x + newInterval;
                next_y = halfPicBoxHeight - data[i + 1] / y_scale;
                //绘制折线
                g.DrawLine(pen, new Point(x, y), new Point(next_x, next_y));
                //绘制纵向虚线
                //g.DrawLine(pen_dash, new Point(x, 0), new Point(x, picBoxHeight));
                //为下个点赋值
                x = next_x;
                y = next_y;
            }
            g.DrawLine(pen_dash, new Point(x + newInterval, 0), new Point(x + newInterval, picBoxHeight));

            //绘制纵向虚线和横坐标，约100个像素间隔
            int interval_count = 100 / newInterval;              //每条虚线相隔多少个newInterval，即相隔多少个数据，设为50的倍数
            interval_count /= 50;
            interval_count *= 50;
            //如果interval_count太小
            if (interval_count == 0)
            {
                interval_count = 50;
            }
            int interval_dash = interval_count * newInterval;   //每条虚线相隔多少像素
            int interval_total = boundary / interval_count + 1;     //共有多少虚线
            int currX = 0;
            int currValue = 0;
            //设置坐标值的字体
            Font font = new Font("宋体", 10, FontStyle.Bold);
            for (int i = 0; i < interval_total; i ++)
            {
                g.DrawLine(pen_dash, new Point(currX, 0), new Point(currX, picBoxHeight));
                g.DrawString(currValue.ToString(), font, Brushes.DimGray, new Point(currX, picBoxHeight));
                currX += interval_dash;
                currValue += interval_count;
            }

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
            //如果数据不需要缩放
            if (y_data_scale == 1)
            {
                //绘制横向实线和坐标值
                //上半部横向实线和坐标值
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
                //下半部横向实线和坐标值
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
            }
            //如果数据需要缩放
            else
            {
                //设置double类型的y轴坐标值
                double y_line_double = 0f;
                //绘制横向实线和坐标值
                //上半部横向实线和坐标值
                for (int i = 0; i < y_count; i++)
                {
                    y_line += y_interval;
                    y_line_double = y_line * 1.0 / y_data_scale;
                    y_line_pix = y_line / y_scale;
                    y_line_pix_real = halfPicBoxHeight - y_line_pix;
                    //绘制横向实线
                    g.DrawLine(pen_black, new Point(x_offset, y_line_pix_real), new Point(picBoxWidth, y_line_pix_real));
                    //绘制坐标值
                    g_axis.DrawString(y_line_double.ToString("f4"), font, Brushes.DimGray, new Point(0, y_line_pix_real));
                }
                //下半部横向实线和坐标值
                y_line = 0;
                for (int i = 0; i < y_count; i++)
                {
                    y_line -= y_interval;
                    y_line_double = y_line * 1.0 / y_data_scale;
                    y_line_pix = y_line / y_scale;
                    y_line_pix_real = halfPicBoxHeight - y_line_pix;
                    //绘制横向实线
                    g.DrawLine(pen_black, new Point(x_offset, y_line_pix_real), new Point(picBoxWidth, y_line_pix_real));
                    //绘制坐标值
                    g_axis.DrawString(y_line_double.ToString("f4"), font, Brushes.DimGray, new Point(0, y_line_pix_real));
                }
                //零点坐标值
                g_axis.DrawString(0.ToString(), font, Brushes.DimGray, new Point(0, halfPicBoxHeight));
                g.DrawLine(pen_dash, new Point(x_offset, 0), new Point(picBoxWidth, 0));
                g.DrawLine(pen_dash, new Point(x_offset, picBoxHeight), new Point(picBoxWidth, picBoxHeight));
            }
            

            pictureBox.Image = bitmap;
            pictureBox_axis.Image = bitmap_axis;
            g.Dispose();
            g_axis.Dispose();
            //interval = newInterval;
        }

        /*此函数用于绘制bitmap的压缩版
         * data:读取的数据
         * newInterval:需要设置的数据显示间隔，负数，如-2：每两个点相隔一个像素，-3：每三个点相隔一个像素。。
         * y_data_scale:表示原数据被放大了多少倍
         */
        private void drawWaveCompress(short[] data, int newInterval, int y_data_scale)
        {
            //如果数据为空则返回
            if (data == null || newInterval >= -1 || newInterval < -5 || this.Height < 10 || y_data_scale < 1)
            {
                return;
            }
            newInterval = -newInterval;
            panel_picture.Height = this.Height;
            panel_axis.Height = this.Height;
            int dataSize = data.Length - 2;
            //表格向右移若干像素点，留空间写坐标值
            int x_offset = 0;
            //获取data大小
            int dataLength = dataSize;
            //计算pictureBox大小
            int picBoxWidth = dataLength / newInterval + 1;
            int picBoxHeight = (panel_picture.Height - 40);
            int halfPicBoxHeight = picBoxHeight / 2;
            //生成位图
            if (bitmap != null)
            {
                bitmap.Dispose();
            }
            if (bitmap_axis != null)
            {
                bitmap_axis.Dispose();
            }

            bitmap = new Bitmap(picBoxWidth, picBoxHeight + 20);
            bitmap_axis = new Bitmap(pictureBox_axis.Width, bitmap.Height);
            Graphics g = Graphics.FromImage(bitmap);
            Graphics g_axis = Graphics.FromImage(bitmap_axis);
            g.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;
            //各种画线
            //绘制表格
            //设置背景为白色
            g.Clear(Color.White);
            g_axis.Clear(Color.White);
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
            short data_max1 = 0;
            short data_max2 = 0;
            //如果最大、最小值是-32768，不进行取绝对值，否则会报错
            if (data[dataSize] == -32768 || data[dataSize + 1] == -32768)
            {
                max = 32768;
            }
            else
            {
                data_max1 = System.Math.Abs(data[dataSize + 1]);
                data_max2 = System.Math.Abs(data[dataSize]);
                max = Convert.ToInt32(data_max1 > data_max2 ? data_max1 : data_max2);
            }

            int y_scale = (max / halfPicBoxHeight) + 1;
            int x = 0 + x_offset;
            int y = 0;
            int next_y = 0;
            //存放每组压缩数据的最大、最小值
            short max_temp = 0;
            short min_temp = 0;
            //两个数据压缩到一个像素时
            if (newInterval == 2)
            {
                int n = dataSize % 2;
                int m = boundary - n;
                int i = 0;
                for (i = 1; i < m; i += 2)
                {
                    y = halfPicBoxHeight - data[i] / y_scale;
                    g.DrawLine(pen, new Point(x, y), new Point(x - 1, halfPicBoxHeight - data[i - 1] / y_scale));
                    next_y = halfPicBoxHeight - data[i + 1] / y_scale;
                    g.DrawLine(pen, new Point(x, y), new Point(x, next_y));
                    x++;
                }
                if (n == 1)
                {
                    y = halfPicBoxHeight - data[i - n] / y_scale;
                    next_y = halfPicBoxHeight - data[i] / y_scale;
                    g.DrawLine(pen, new Point(x - 1, y), new Point(x, next_y));
                }
            }
            //多于两个像素压缩到一个数据时
            else if (newInterval > 2)
            {
                //计算压缩的组数和余数
                int n = dataSize % newInterval; //压缩为m组后剩余n个
                int m = boundary - n;           //压缩为m组
                int i = 0, j = 0;
                short temp;
                for (i = 1, j = 0; i < m;)
                {
                    //画一条连接该组和上一组的连线
                    g.DrawLine(pen, new Point(x, halfPicBoxHeight - data[i] / y_scale), new Point(x - 1, halfPicBoxHeight - data[i - 1] / y_scale));
                    //计算每组的最大、最小值
                    if (data[i] > data[i + 1])
                    {
                        max_temp = data[i];
                        min_temp = data[i + 1];
                    }
                    else
                    {
                        max_temp = data[i + 1];
                        min_temp = data[i];
                    }
                    for (j = 2; j < newInterval; j++)
                    {
                        temp = data[i + j];
                        if (temp > max_temp)
                        {
                            max_temp = temp;
                        }
                        else if (temp < min_temp)
                        {
                            min_temp = temp;
                        }
                    }
                    y = halfPicBoxHeight - max_temp / y_scale;
                    next_y = halfPicBoxHeight - min_temp / y_scale;
                    //在最大、最小值间连线
                    g.DrawLine(pen, new Point(x, y), new Point(x, next_y));
                    x++;
                    i += newInterval;
                }
                if (n != 0)
                {
                    n = n - 1;
                    i = i - n;
                    g.DrawLine(pen, new Point(x, halfPicBoxHeight - data[i] / y_scale), new Point(x - 1, halfPicBoxHeight - data[i - 1] / y_scale));
                    for (; i < n; i++)
                    {
                        if (data[i] > data[i + 1])
                        {
                            max_temp = data[i];
                            min_temp = data[i + 1];
                        }
                        else
                        {
                            max_temp = data[i + 1];
                            min_temp = data[i];
                        }
                        y = halfPicBoxHeight - max_temp / y_scale;
                        next_y = halfPicBoxHeight - min_temp / y_scale;
                        g.DrawLine(pen, new Point(x, y), new Point(x, next_y));
                        x++;
                    }
                }
            }
            g.DrawLine(pen_dash, new Point(x + newInterval, 0), new Point(x + newInterval, picBoxHeight));

            //绘制纵向虚线和横坐标，约100个像素间隔
            int interval_count = 100 * newInterval;              //每条虚线相隔多少个newInterval，即相隔多少个数据
            int interval_dash = 100;   //每条虚线相隔多少像素，由于是压缩，直接设为100
            int interval_total = boundary / interval_count + 1;     //共有多少虚线
            int currX = 0;
            int currValue = 0;
            //设置坐标值的字体
            Font font = new Font("宋体", 10, FontStyle.Bold);
            for (int i = 0; i < interval_total; i++)
            {
                g.DrawLine(pen_dash, new Point(currX, 0), new Point(currX, picBoxHeight));
                g.DrawString(currValue.ToString(), font, Brushes.DimGray, new Point(currX, picBoxHeight));
                currX += interval_dash;
                currValue += interval_count;
            }

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
            //如果数据不需要缩放
            if (y_data_scale == 1)
            {
                //绘制横向实线和坐标值
                //上半部横向实线和坐标值
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
                //下半部横向实线和坐标值
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
            }
            //如果数据需要缩放
            else
            {
                //设置double类型的y轴坐标值
                double y_line_double = 0f;
                //绘制横向实线和坐标值
                //上半部横向实线和坐标值
                for (int i = 0; i < y_count; i++)
                {
                    y_line += y_interval;
                    y_line_double = y_line * 1.0 / y_data_scale;
                    y_line_pix = y_line / y_scale;
                    y_line_pix_real = halfPicBoxHeight - y_line_pix;
                    //绘制横向实线
                    g.DrawLine(pen_black, new Point(x_offset, y_line_pix_real), new Point(picBoxWidth, y_line_pix_real));
                    //绘制坐标值
                    g_axis.DrawString(y_line_double.ToString("f4"), font, Brushes.DimGray, new Point(0, y_line_pix_real));
                }
                //下半部横向实线和坐标值
                y_line = 0;
                for (int i = 0; i < y_count; i++)
                {
                    y_line -= y_interval;
                    y_line_double = y_line * 1.0 / y_data_scale;
                    y_line_pix = y_line / y_scale;
                    y_line_pix_real = halfPicBoxHeight - y_line_pix;
                    //绘制横向实线
                    g.DrawLine(pen_black, new Point(x_offset, y_line_pix_real), new Point(picBoxWidth, y_line_pix_real));
                    //绘制坐标值
                    g_axis.DrawString(y_line_double.ToString("f4"), font, Brushes.DimGray, new Point(0, y_line_pix_real));
                }
                //零点坐标值
                g_axis.DrawString(0.ToString(), font, Brushes.DimGray, new Point(0, halfPicBoxHeight));
                g.DrawLine(pen_dash, new Point(x_offset, 0), new Point(picBoxWidth, 0));
                g.DrawLine(pen_dash, new Point(x_offset, picBoxHeight), new Point(picBoxWidth, picBoxHeight));
            }

            pictureBox.Image = bitmap;
            pictureBox_axis.Image = bitmap_axis;
            g.Dispose();
            g_axis.Dispose();
            //interval = newInterval;
        }

        /*此函数用于外部调用的绘制接口
         * data: 要用于绘制图标的short型数组
         * interval: 每个数据间的像素间隔，若为负数，则将每-interval个数据压缩到一个像素上
         * return: true为参数正常，false为参数出错
         */
        public bool reDraw(short[] data, int interval)
        {
            if (data == null)
            {
                return false;
            }

            if (interval > 0)
            {
                drawWave(data, interval, 1);
            }
            else if (interval < -1)
            {
                drawWaveCompress(data, interval, 1);
            }
            else
            {
                return false;
            }

            return true;
        }
        /*此函数用于外部调用的绘制接口,reDraw的重载
         * data: 要用于绘制图标的short型数组
         * interval: 每个数据间的像素间隔，若为负数，则将每-interval个数据压缩到一个像素上
         * y_data_scale:表示原数据被放大了多少倍
         * return: true为参数正常，false为参数出错
         */
        public bool reDraw(short[] data, int interval, int y_data_scale)
        {
            if (data == null)
            {
                return false;
            }

            if (interval > 0)
            {
                drawWave(data, interval, y_data_scale);
            }
            else if (interval < -1)
            {
                drawWaveCompress(data, interval, y_data_scale);
            }
            else
            {
                return false;
            }

            return true;
        }

        /*此函数用于保存图表的bitmap版本
         * savePath: 保存路径
         * return: true表示保存正常，false表示保存出错
         */
        public bool saveBitmap(string savePath)
        {
            try
            {
                FileStream fileStream = new FileStream(savePath, FileMode.Create);
                //新建一个bitmap来拼接bitmap_axis和bitmap
                Bitmap bitmap_joint = new Bitmap(bitmap_axis.Width + bitmap.Width, bitmap.Height);
                //进行拼接
                Graphics g = Graphics.FromImage(bitmap_joint);
                g.DrawImage(bitmap_axis, 0, 0);
                g.DrawImage(bitmap, bitmap_axis.Width, 0);
                //进行保存
                bitmap_joint.Save(fileStream, System.Drawing.Imaging.ImageFormat.Png);
                //释放bitmap_joint
                g.Dispose();
                bitmap_joint.Dispose();
                //关闭fs
                fileStream.Flush();
                fileStream.Close();
            }
            catch
            {
                return false;
            }

            return true;
        }

    }
    
}
