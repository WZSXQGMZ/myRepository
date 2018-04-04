using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Runtime.InteropServices;

namespace CalculatorWithInterface
{
    public static class Program
    {
        /// <summary>
        /// 应用程序的主入口点。
        /// </summary>
        [STAThread]
        [DllImport(@"CalculatorCreatDLL.dll", EntryPoint = "toPostfixExpression", CallingConvention = CallingConvention.Cdecl)]
        extern public static void toPostfixExpression(byte[] infixExpression, byte[] strPsExp);
        [DllImport(@"CalculatorCreatDLL.dll", EntryPoint = "expressionCheck", CallingConvention = CallingConvention.Cdecl)]
        extern public static int expressionCheck(byte[] infixExpression);
        [DllImport(@"CalculatorCreatDLL.dll", EntryPoint = "calculatePex", CallingConvention = CallingConvention.Cdecl)]
        extern public static double calculatePex(byte[] wctPsExp);

        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }
    }
}
