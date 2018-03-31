using System;
using System.Windows.Forms;

using System.Threading;

namespace DrawWave
{
    public partial class FormMain : Form
    {
        //存放打开文件路径的字符串
        private string filePath = null;

        //构造函数
        public FormMain()
        {
            InitializeComponent();
            //this.BackColor = Color.White;

            mainPanel.AutoScroll = true;
            mainPanel.Anchor = (AnchorStyles.Top | AnchorStyles.Left);
            mainPanel.Dock = DockStyle.Fill;

        }

        //打开文件的按钮
        private void buttonAdd_Click(object sender, EventArgs e)
        {
            //drawWaveFunc();
            //panel1.Controls.Add(new PanelPictureBox("E:\\VSWorkSpace\\SoftwareStructure\\test.dat"));

            //新建一个线程，否则直接打开OpenFileDialog会出错
            Thread td = new Thread(delegate () { callOpenFileDialog(); });
            td.SetApartmentState(ApartmentState.STA);
            td.Start();
            while (td.IsAlive)
            {
                Application.DoEvents();
            }
            if (filePath != null) {
                //改变鼠标为等待样式
                try
                {
                    Cursor = Cursors.WaitCursor;
                }
                catch { }
                PanelPictureBox temp_ppb = new PanelPictureBox(filePath);
                if (temp_ppb.Created())
                {
                    mainPanel.Controls.Add(temp_ppb);
                }else
                {
                    temp_ppb.myDispose();
                }
                //改变鼠标为默认样式
                try
                {
                    Cursor = Cursors.Default;
                }
                catch { }
                filePath = null;
            }
        }

        //打开文件对话框的函数
        private void callOpenFileDialog()
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Filter = "所有文件(*.*)|*.*";
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
        
    }
}
