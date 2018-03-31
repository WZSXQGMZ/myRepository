using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.Windows.Forms;
using System.Drawing;

namespace DrawWave
{
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

        public PanelChart()
        {
            panel_chart_initialize();
        }

        public void myDispose()
        {
            try
            {
                bitmap_axis.Dispose();
                bitmap.Dispose();
                pictureBox_axis.Dispose();
                pictureBox.Dispose();
                panel_axis.Dispose();
                panel_picture.Dispose();
                this.Dispose();
            }
            catch
            {
                MessageBox.Show("关闭失败");
            }
        }

        //初始化panel_chart的函数
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
         */
        private void drawWave(short[] data, int newInterval)
        {
            //如果数据为空则返回
            if (data == null || newInterval <= 0 || newInterval > 30 || this.Height < 10)
            {
                return;
            }
            panel_picture.Height = this.Height;
            panel_axis.Height = this.Height;
            int dataSize = data.Length - 2;
            //表格向右移若干像素点，留空间写坐标值
            int x_offset = 0;
            //获取data大小
            int dataLength = dataSize;
            //计算pictureBox大小
            int picBoxWidth = newInterval * dataLength;
            int picBoxHeight = (panel_picture.Height - 20);
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
            //interval = newInterval;
        }

        /*此函数用于绘制bitmap的压缩版
         * data:读取的数据
         * newInterval:需要设置的数据显示间隔，负数，如-2：每两个点相隔一个像素，-3：每三个点相隔一个像素。。
         */
        private void drawWaveCompress(short[] data, int newInterval)
        {
            //如果数据为空则返回
            if (data == null || newInterval >= -1 || newInterval <-5)
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
            int picBoxWidth = dataLength / newInterval;
            int picBoxHeight = (panel_picture.Height - 20);
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
            int x = -1 + x_offset;
            int y = 0;
            int next_x = 0;
            int next_y = 0;
            for (int i = 0, j = 1; i < boundary; i++, j++)
            {
                //计算连续两点的位置
                if (j == 1)
                {
                    x++;
                }
                y = halfPicBoxHeight - data[i] / y_scale;
                if(j > newInterval){ 
                    next_x = x + 1;
                    j = 0;
                }
                next_y = halfPicBoxHeight - data[i + 1] / y_scale;
                //绘制折线
                g.DrawLine(pen, new Point(x, y), new Point(next_x, next_y));
                if (j == 1) {
                    //绘制纵向虚线
                    //g.DrawLine(pen_dash, new Point(x, 0), new Point(x, picBoxHeight));
                }
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
            //interval = newInterval;
        }

        public bool reDraw(short[] data, int interval)
        {
            if (data == null)
            {
                return false;
            }

            if (interval > 0)
            {
                drawWave(data, interval);
            }
            else if (interval < -1)
            {
                drawWaveCompress(data, interval);
            }
            else
            {
                return false;
            }

            return true;
        }
    }
    
}
