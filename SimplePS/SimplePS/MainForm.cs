using System;
using System.Drawing;
using System.IO;
using System.Threading;
using System.Windows.Forms;

namespace SimplePS
{
    public partial class MainForm : Form
    {
        OpBitmap opBitmap = null;

        bool oprated = false;

        string filePath = null;
        string savePath = null;

        public MainForm()
        {
            InitializeComponent();
            opBitmap = new OpBitmap();
            this.Text = "SimplePS";
        }
        
        //另存为按钮
        private void SaveAsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if(oprated == false)
            {
                return;
            }

            //新建一个线程，否则直接打开OpenFileDialog会出错
            Thread td = new Thread(new ParameterizedThreadStart(callSaveFileDialog));
            td.SetApartmentState(ApartmentState.STA);
            //传入保存的文件类型参数
            td.Start("bmp图像(*.bmp)|*.bmp");
            while (td.IsAlive)
            {
                Application.DoEvents();
            }
            if (savePath == null || savePath == "")
            {
                return;
            }

            // 改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { }
            //保存文件
            saveImage(savePath);
            filePath = savePath;
            this.Text = filePath;
            savePath = null;

            oprated = false;
            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { }

        }

        //保存按钮
        private void SaveToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if(oprated == false)
            {
                return;
            }
            saveImage(filePath);
            oprated = false;

        }
        //保存图像函数
        void saveImage(string path)
        {
            // 改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { }

            if (oprated == false)
            {
                return;
            }

            opBitmap.saveBitmap(path);

            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { }
        }

        //打开文件按钮
        private void OpenFileToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //新建一个线程，否则直接打开OpenFileDialog会出错
            Thread td = new Thread(delegate () { callOpenFileDialog(); });
            td.SetApartmentState(ApartmentState.STA);
            td.Start();
            while (td.IsAlive)
            {
                Application.DoEvents();
            }

            //改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { };

            if (filePath != null)
            {


                //读取图片
                if(opBitmap.getBitmap(filePath) == false)
                {
                    MessageBox.Show("打开失败");
                    try { Cursor = Cursors.Default; } catch { };
                    return;
                }
                //放入pictureBox
                pictureBox.Width = opBitmap.opratedBitmap.Width;
                pictureBox.Height = opBitmap.opratedBitmap.Height;
                pictureBox.Image = opBitmap.opratedBitmap;
                this.Text = filePath;


            }

            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { };

        }

        //打开文件对话框的函数
        private void callOpenFileDialog()
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Filter = "bmp文件(*.bmp)|*.bmp";
            openFileDialog.RestoreDirectory = true;
            openFileDialog.Title = "选择文件";
            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                filePath = openFileDialog.FileName;
                if (filePath == null)
                {
                    MessageBox.Show("打开文件失败");
                }
            }
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
                saveFileDialog.FileName = Path.GetFileNameWithoutExtension(filePath) + ".dat";
            }
            //否则不加后缀名
            else
            {
                saveFileDialog.FileName = Path.GetFileNameWithoutExtension(filePath);
            }
            while (true)
            {
                //如果选定文件
                if (saveFileDialog.ShowDialog() == DialogResult.OK)
                {
                    savePath = saveFileDialog.FileName;

                    //如果文件名为空
                    if (saveFileDialog.FileName == null || saveFileDialog.FileName == "")
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
        
        private void TurnToGrayImageItem_Click(object sender, EventArgs e)
        {

            //改变鼠标为等待样式
            try{ Cursor = Cursors.WaitCursor;}catch { }
            opBitmap.turnGrayImage();
            //放入pictureBox
            pictureBox.Width = opBitmap.opratedBitmap.Width;
            pictureBox.Height = opBitmap.opratedBitmap.Height;
            pictureBox.Image = opBitmap.opratedBitmap;

            oprated = true;
            //改变鼠标为默认样式
            try{Cursor = Cursors.Default;}catch { }
        }
        
        private void EnhanceItem_Click(object sender, EventArgs e)
        {
            //改变鼠标为等待样式
            try
            {
                Cursor = Cursors.WaitCursor;
            }
            catch { }

            opBitmap.enhanceGrayImage();
            //放入pictureBox
            pictureBox.Width = opBitmap.opratedBitmap.Width;
            pictureBox.Height = opBitmap.opratedBitmap.Height;
            pictureBox.Image = opBitmap.opratedBitmap;

            oprated = true;

            //改变鼠标为默认样式
            try
            {
                Cursor = Cursors.Default;
            }
            catch { }
        }
        
        private void TurnBWImage1_1Item_Click(object sender, EventArgs e)
        {

            //改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor;}catch { };

            opBitmap.turnBWImage(128);
            //放入pictureBox
            pictureBox.Width = opBitmap.opratedBitmap.Width;
            pictureBox.Height = opBitmap.opratedBitmap.Height;
            pictureBox.Image = opBitmap.opratedBitmap;

            oprated = true;

            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { };
        }
        
        private void TurnBWImage1_4Item_Click(object sender, EventArgs e)
        {

            //改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { };

            opBitmap.dither(2);
            //放入pictureBox
            pictureBox.Width = opBitmap.opratedBitmap.Width;
            pictureBox.Height = opBitmap.opratedBitmap.Height;
            pictureBox.Image = opBitmap.opratedBitmap;

            oprated = true;

            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { };
        }

        private void TurnBWImage1_16Item_Click(object sender, EventArgs e)
        {

            //改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { };

            opBitmap.dither(4);
            //放入pictureBox
            pictureBox.Width = opBitmap.opratedBitmap.Width;
            pictureBox.Height = opBitmap.opratedBitmap.Height;
            pictureBox.Image = opBitmap.opratedBitmap;

            oprated = true;

            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { };
        }

        private void TurnBWImage4Item_Click(object sender, EventArgs e)
        {

            //改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { };

            opBitmap.orderDither(2);
            //放入pictureBox
            pictureBox.Width = opBitmap.opratedBitmap.Width;
            pictureBox.Height = opBitmap.opratedBitmap.Height;
            pictureBox.Image = opBitmap.opratedBitmap;

            oprated = true;

            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { };
        }

        private void TurnBWImage16Item_Click(object sender, EventArgs e)
        {

            //改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { };

            opBitmap.orderDither(4);
            //放入pictureBox
            pictureBox.Width = opBitmap.opratedBitmap.Width;
            pictureBox.Height = opBitmap.opratedBitmap.Height;
            pictureBox.Image = opBitmap.opratedBitmap;

            oprated = true;

            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { };
        }

        private void RollbackItem_Click(object sender, EventArgs e)
        {
            if(oprated == false || opBitmap.currBitmap == null)
            {
                return;
            }

            //改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { };

            if (opBitmap.opratedBitmap != null)
            {
                opBitmap.opratedBitmap.Dispose();
            }
            opBitmap.opratedBitmap = new Bitmap(opBitmap.currBitmap);
            //放入pictureBox
            pictureBox.Width = opBitmap.opratedBitmap.Width;
            pictureBox.Height = opBitmap.opratedBitmap.Height;
            pictureBox.Image = opBitmap.opratedBitmap;

            oprated = false;

            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { };
        }

        private void EnhanceColorMenuItem_Click(object sender, EventArgs e)
        {
            //改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { };

            opBitmap.enhanceColorImage();
            //放入pictureBox
            pictureBox.Width = opBitmap.opratedBitmap.Width;
            pictureBox.Height = opBitmap.opratedBitmap.Height;
            pictureBox.Image = opBitmap.opratedBitmap;

            oprated = true;

            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { };
        }

        private void thresholdTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if(e.KeyCode == Keys.Enter)
            {
                try
                {
                    Cursor = Cursors.WaitCursor;
                    int threshold = Convert.ToInt32(thresholdTextBox.Text);
                    if(threshold < 0 || threshold > 255)
                    {
                        Cursor = Cursors.Default;
                        return;
                    }

                    opBitmap.turnBWImage(threshold);
                    //放入pictureBox
                    pictureBox.Width = opBitmap.opratedBitmap.Width;
                    pictureBox.Height = opBitmap.opratedBitmap.Height;
                    pictureBox.Image = opBitmap.opratedBitmap;

                    oprated = true;

                    Cursor = Cursors.Default;
                }
                catch
                {
                    Cursor = Cursors.Default;
                }
            }
        }

        private void ditherTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                try
                {
                    Cursor = Cursors.WaitCursor;
                    int dim = Convert.ToInt32(ditherTextBox.Text);
                    if (dim < 2 || opBitmap.powerOf2(dim) == false)
                    {
                        Cursor = Cursors.Default;
                        return;
                    }
                    if (dim > 256)
                    {
                        Cursor = Cursors.Default;
                        MessageBox.Show("倍数过大");
                        return;
                    }

                    opBitmap.dither(dim);
                    //放入pictureBox
                    pictureBox.Width = opBitmap.opratedBitmap.Width;
                    pictureBox.Height = opBitmap.opratedBitmap.Height;
                    pictureBox.Image = opBitmap.opratedBitmap;

                    oprated = true;

                    Cursor = Cursors.Default;
                }
                catch
                {
                    Cursor = Cursors.Default;
                }
            }
        }

        private void orderedDitherTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                try
                {
                    Cursor = Cursors.WaitCursor;
                    int dim = Convert.ToInt32(orderedDitherTextBox.Text);
                    if (dim < 2 || opBitmap.powerOf2(dim) == false)
                    {
                        Cursor = Cursors.Default;
                        return;
                    }
                    if(dim > 16)
                    {
                        Cursor = Cursors.Default;
                        MessageBox.Show("倍数过大");
                        return;
                    }

                    opBitmap.orderDither(dim);
                    //放入pictureBox
                    pictureBox.Width = opBitmap.opratedBitmap.Width;
                    pictureBox.Height = opBitmap.opratedBitmap.Height;
                    pictureBox.Image = opBitmap.opratedBitmap;

                    oprated = true;

                    Cursor = Cursors.Default;
                }
                catch
                {
                    Cursor = Cursors.Default;
                }
            }
        }

        private void LosslessPreItem_Click(object sender, EventArgs e)
        {
            //改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { };

            opBitmap.losslessPre();
            //放入pictureBox
            pictureBox.Width = opBitmap.opratedBitmap.Width;
            pictureBox.Height = opBitmap.opratedBitmap.Height;
            pictureBox.Image = opBitmap.opratedBitmap;

            oprated = true;

            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { };
        }

        private void UniformQuatizationItem_Click(object sender, EventArgs e)
        {
            //改变鼠标为等待样式
            try { Cursor = Cursors.WaitCursor; } catch { };

            opBitmap.uniformQuatization(4);
            //放入pictureBox
            pictureBox.Width = opBitmap.opratedBitmap.Width;
            pictureBox.Height = opBitmap.opratedBitmap.Height;
            pictureBox.Image = opBitmap.opratedBitmap;

            oprated = true;

            //改变鼠标为默认样式
            try { Cursor = Cursors.Default; } catch { };
        }

        private void compRatioToolStripMenuItem_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                try
                {
                    Cursor = Cursors.WaitCursor;
                    int bitRemain = Convert.ToInt32(compRatioTextBox.Text);
                    if (bitRemain < 1 || bitRemain > 7)
                    {
                        Cursor = Cursors.Default;
                        return;
                    }

                    opBitmap.uniformQuatization(bitRemain);
                    //放入pictureBox
                    pictureBox.Width = opBitmap.opratedBitmap.Width;
                    pictureBox.Height = opBitmap.opratedBitmap.Height;
                    pictureBox.Image = opBitmap.opratedBitmap;

                    oprated = true;

                    Cursor = Cursors.Default;
                }
                catch
                {
                    Cursor = Cursors.Default;
                }
            }
        }

        private void DctDimTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                try
                {
                    Cursor = Cursors.WaitCursor;
                    int dim = Convert.ToInt32(DctDimTextBox.Text);
                    if (dim < 2 || dim > 64)
                    {
                        Cursor = Cursors.Default;
                        return;
                    }

                    opBitmap.dct(dim, 0);
                    //放入pictureBox
                    pictureBox.Width = opBitmap.opratedBitmap.Width;
                    pictureBox.Height = opBitmap.opratedBitmap.Height;
                    pictureBox.Image = opBitmap.opratedBitmap;

                    oprated = true;

                    Cursor = Cursors.Default;
                }
                catch
                {
                    Cursor = Cursors.Default;
                }
            }
        }
        private void IDctDimTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                try
                {
                    Cursor = Cursors.WaitCursor;
                    int dim = Convert.ToInt32(IDctDimTextBox.Text);
                    if (dim < 2 || dim > 64)
                    {
                        Cursor = Cursors.Default;
                        return;
                    }

                    opBitmap.dct(dim, 1);
                    //放入pictureBox
                    pictureBox.Width = opBitmap.opratedBitmap.Width;
                    pictureBox.Height = opBitmap.opratedBitmap.Height;
                    pictureBox.Image = opBitmap.opratedBitmap;

                    oprated = true;

                    Cursor = Cursors.Default;
                }
                catch
                {
                    Cursor = Cursors.Default;
                }
            }
        }
        private void HalfIDctDimTextBox_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                try
                {
                    Cursor = Cursors.WaitCursor;
                    int dim = Convert.ToInt32(HalfIDctDimTextBox.Text);
                    if (dim < 2 || dim > 64)
                    {
                        Cursor = Cursors.Default;
                        return;
                    }

                    opBitmap.dct(dim, 2);
                    //放入pictureBox
                    pictureBox.Width = opBitmap.opratedBitmap.Width;
                    pictureBox.Height = opBitmap.opratedBitmap.Height;
                    pictureBox.Image = opBitmap.opratedBitmap;

                    oprated = true;

                    Cursor = Cursors.Default;
                }
                catch
                {
                    Cursor = Cursors.Default;
                }
            }
        }

        private void pictureBox_Click(object sender, EventArgs e)
        {

        }
    }
}
