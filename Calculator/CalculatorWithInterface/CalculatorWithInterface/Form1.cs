using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Threading;

namespace CalculatorWithInterface
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }
        string filePath;    //用于存储txt文件路径
        string[] allFileLines=new string[1];//用于存储txt文件的每一行
        int lineCount = 0;  //用于记录当前行数
        
        //以下为运算符及数字按钮按下时的操作
        private void buttonSin_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "sin";
        }

        private void buttonCos_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "cos";
        }

        private void buttonTan_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "tan";
        }

        private void buttonAdd_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "+";
        }

        private void buttonLog_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "log";
        }

        private void buttonLn_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "ln";
        }

        private void buttonPow_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "^";
        }

        private void buttonSub_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "-";
        }

        private void buttonLb_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "(";
        }

        private void buttonRb_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += ")";
        }

        private void buttonRad_Click(object sender, EventArgs e)
        {
            textBoxReasult.Text += "√暂不可用( ´_ゝ`)";
        }

        private void buttonMul_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "*";
        }

        private void buttonDiv_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "/";
        }

        private void button1_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "1";
        }

        private void button2_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "2";
        }

        private void button3_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "3";
        }

        private void button4_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "4";
        }

        private void button5_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "5";
        }

        private void button6_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "6";
        }

        private void button7_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "7";
        }

        private void button8_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "8";
        }

        private void button9_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "9";
        }

        private void button0_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += "0";
        }

        private void buttonDoc_Click(object sender, EventArgs e)
        {
            textBoxExp.Text += '.';
        }

        private void buttonEqu_Click(object sender, EventArgs e)
        {
            //当等号按钮按下时
            byte[] postfixExpression = new byte[1024];
            textBoxReasult.Text = "";
            //判断textBoxExp中的表达式是否合法
            if (Program.expressionCheck(System.Text.Encoding.Default.GetBytes(textBoxExp.Text)) == 0)
            {
                //不合法则显示提示
                textBoxReasult.Text = "error expression";
                return;
            }
            //转换为后缀表达式
            Program.toPostfixExpression(System.Text.Encoding.Default.GetBytes(textBoxExp.Text), postfixExpression);
            //计算后缀表达式并显示结果
            textBoxReasult.Text = Program.calculatePex(postfixExpression).ToString();
        }

        private void buttonToPoExp_Click(object sender, EventArgs e)
        {
            //当显示后缀表达式按钮按下时
            byte[] postfixExpression = new byte[1024];
            textBoxReasult.Text = "";
            //判断textBoxExp中的表达式是否合法
            if (Program.expressionCheck(System.Text.Encoding.Default.GetBytes(textBoxExp.Text)) == 0)
            {
                //不合法则显示提示
                textBoxReasult.Text = "error expression";
                return;
            }
            //转换为后缀表达式
            Program.toPostfixExpression(System.Text.Encoding.Default.GetBytes(textBoxExp.Text), postfixExpression);
            //显示后缀表达式
            textBoxReasult.Text = System.Text.Encoding.Default.GetString(postfixExpression);
        }

        private void buttonBackSpace_Click(object sender, EventArgs e)
        {
            //当推格按钮按下时，删除textBoxExp的最后一个字符
            if((textBoxExp.Text.Length - 1) < 0)
            {
                return;
            }
            textBoxExp.Text = textBoxExp.Text.Substring(0, textBoxExp.Text.Length - 1);
        }

        private void buttonReadFile_Click(object sender, EventArgs e)
        {
            //新建一个线程，否则直接打开OpenFileDialog会出错
            Thread td = new Thread(delegate () { call(); });
            td.SetApartmentState(ApartmentState.STA);
            td.Start();
            textBoxExp.Text = allFileLines[0];
        }

        private void buttonPre_Click(object sender, EventArgs e)
        {
            //当上一行按钮按下时，显示文件中的上一行
            if (lineCount == 0)
            {
                textBoxExp.Text = allFileLines[0];
            }
            else
            {
                textBoxExp.Text = allFileLines[--lineCount];
            }
        }

        private void buttonNext_Click(object sender, EventArgs e)
        {
            //当下一行按钮按下时，显示文件中的下一行
            if (lineCount == allFileLines.Length-1)
            {
                textBoxExp.Text = allFileLines[allFileLines.Length-1];
            }
            else
            {
                textBoxExp.Text = allFileLines[++lineCount];
            }
        }

        //用于建立OpenFileDialog的函数
        public void call()
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Filter = "文本文件(*.txt)|*.txt";
            openFileDialog.RestoreDirectory = true;
            openFileDialog.Title = "选择文本文件";
            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                filePath = openFileDialog.FileName;
                if (filePath != null)
                {
                    allFileLines = File.ReadAllLines(filePath);
                    lineCount = 0;
                }
            }
        }
    }
}
