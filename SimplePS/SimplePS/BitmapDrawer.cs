
using System.Drawing;
using System;

namespace SimplePS
{
    class BitmapDrawer
    {
        private Bitmap bitmap = null;
        int width;
        int height;
        static private int[,] OneMatrixValue = new int[2,2]{ { 0, 2 } , { 3, 1 } };
        public BitmapDrawer(Bitmap bitmap)
        {
            setBitmap(bitmap);
        }

        public BitmapDrawer()
        {

        }

        public void setBitmap(Bitmap bitmap)
        {
            this.bitmap = bitmap;
            width = bitmap.Width;
            height = bitmap.Height;
        }

        //转灰度图像
        public void turnGrayImage()
        {
            if(bitmap == null)
            {
                return;
            }

            int height = bitmap.Height;
            int width = bitmap.Width;

            Color currColor;
            int tempGray;
            Color grayColor;
            for(int y = 0; y < height; y++)
            {
                for(int x = 0; x < width; x++)
                {
                    currColor = bitmap.GetPixel(x, y);
                    tempGray = ((currColor.R + currColor.G + currColor.B) / 3);
                    grayColor = Color.FromArgb(tempGray, tempGray, tempGray);
                    bitmap.SetPixel(x, y, grayColor);
                }
            }
        }

        //转灰度图像并增强对比度
        public void enhanceImage()
        {
            if (bitmap == null)
            {
                return;
            }
            int height = bitmap.Height;
            int width = bitmap.Width;

            //先转为灰度图像
            Color currColor;
            int tempGray;
            Color grayColor;
            int[] platte = new int[256];
            for(int i = 0; i < 256; i++)
            {
                platte[i] = 0;
            }
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    currColor = bitmap.GetPixel(x, y);
                    tempGray = ((currColor.R + currColor.G + currColor.B) / 3);
                    grayColor = Color.FromArgb(tempGray, tempGray, tempGray);
                    bitmap.SetPixel(x, y, grayColor);

                    platte[tempGray] += 1;
                }
            }
            //计算调色板
            double totalPixel = height * width;
            double colorSum = 0;
            for(int i = 0; i < 256; i++)
            {
                colorSum += platte[i];
                platte[i] = (int)(255 * (colorSum / totalPixel));
            }
            //填充颜色
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    currColor = bitmap.GetPixel(x, y);
                    int gray = platte[currColor.R];
                    bitmap.SetPixel(x, y, Color.FromArgb(gray, gray, gray));
                }
            }
        }

        //转黑白图像1_1
        public void turnBWImage1_1(int threshold)
        {
            if (bitmap == null)
            {
                return;
            }

            if(threshold > 255)
            {
                threshold = 255;
            }else if(threshold < 0)
            {
                threshold = 0;
            }

            int height = bitmap.Height;
            int width = bitmap.Width;

            Color currColor;
            int tempColor;
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    currColor = bitmap.GetPixel(x, y);
                    tempColor = ((currColor.R + currColor.G + currColor.B) / 3);
                    if(tempColor < threshold)
                    {
                        bitmap.SetPixel(x, y, Color.Black);
                    }
                    else
                    {
                        bitmap.SetPixel(x, y, Color.White);
                    }
                }
            }

        }

        //转黑白图像1_x
        public void turnBWImage1_x(int dim)
        {
            if(bitmap == null)
            {
                return;
            }
            //获取size
            int width = bitmap.Width;
            int height = bitmap.Height;

            //生成matrix
            int[,] matrix = new int[dim, dim];
            setDitherMatrix(matrix, dim, 0, 0);

            //将matrix转为阈值形式
            for(int i = 0; i < dim; i++)
            {
                for(int j = 0; j < dim; j++)
                {
                    matrix[i, j] = 255 * matrix[i, j] / (dim * dim);
                }
            }

            //为每个dim*dim块赋值
            for (int j = 0; j < height; j += dim)
            {
                for (int i = 0; i < width; i += dim)
                {
                    int currj = j;
                    for (int stepj = 0; stepj < dim && currj < height;)
                    {
                        int curri = i;
                        for (int stepi = 0; stepi < dim && curri < width;)
                        {
                            Color currPixel = bitmap.GetPixel(curri, currj);
                            if((currPixel.R + currPixel.B + currPixel.G) / 3 >= matrix[stepj, stepi])
                            {
                                bitmap.SetPixel(curri, currj, Color.White);
                            } else
                            {
                                bitmap.SetPixel(curri, currj, Color.Black);
                            }

                            stepi++;
                            curri++;
                        }

                        stepj++;
                        currj++;
                    }
                }
            }
        }

        //转黑白图像*x
        public void turnBWImageX(Bitmap originBitmap, int dim)
        {
            if(originBitmap == null || bitmap == null)
            {
                return;
            }

            int width = bitmap.Width;
            int height = bitmap.Height;

            int originWidth = originBitmap.Width;
            int originHeight = originBitmap.Height;

            if(originWidth * dim != width || originHeight * dim != height)
            {
                return;
            }

            for (int j = 0; j < height; j++)
            {
                for (int i = 0; i < width; i++)
                {
                    bitmap.SetPixel(i, j, Color.Black);
                }
            }

            int[,] matrix = new int[dim, dim];
            setDitherMatrix(matrix, dim, 0, 0);
            int[,] matrixMap = new int[dim * dim, 2];
            for(int i = 0; i < dim; i++)
            {
                for(int j = 0; j < dim; j++)
                {
                    int value = matrix[i, j];
                    matrixMap[value, 0] = i;
                    matrixMap[value, 1] = j;
                }
            }

            int unit = 256 / (dim * dim);

            for (int j = 0; j < originHeight; j++)
            {
                for (int i = 0; i < originWidth; i++)
                {
                    Color currColor = originBitmap.GetPixel(i, j);
                    int threshold = (currColor.R + currColor.B + currColor.G) / 3 / unit;

                    int curri = i * dim;
                    int currj = j * dim;
                    for(int n = 0; n < threshold; n++)
                    {
                        bitmap.SetPixel(matrixMap[n, 0] + curri, matrixMap[n, 1] + currj, Color.White);
                    }
                }
            }

        }

        //增强彩色对比度(HSI)
        public void enhanceColorImage()
        {
            if (bitmap == null)
            {
                return;
            }
            short[,] H = new short[bitmap.Height, bitmap.Width];
            double[,] S = new double[bitmap.Height, bitmap.Width];
            byte[,] I = new byte[bitmap.Height, bitmap.Width];

            //增强对比度后I分量调色板
            int[] palette = new int[256];
            for (int i = 0; i < 256; i++)
            {
                palette[i] = 0;
            }
            //RGB-HSI
            for (int y = 0; y < height; y++)
            {
                for(int x = 0; x < width; x++)
                {
                    Color currColor = bitmap.GetPixel(x, y);
                    int R = currColor.R;
                    int G = currColor.G;
                    int B = currColor.B;
                    int RGBSum = R + G + B;
                    //计算H
                    if(R == G && G == B)
                    {
                        H[y, x] = 0;
                    }else
                    {
                        double theta = Math.Acos(
                            (R - G + R - B) / 2.0 / 
                            Math.Sqrt((R - G) * (R - G) + (R - B) * (G - B))
                            );

                        if(B <= G)
                        {
                            H[y, x] = (short)theta;
                        }else
                        {
                            H[y, x] = (short)(360 - theta);
                        }
                    }
                    //计算S
                    if(RGBSum == 0)
                    {
                        S[y, x] = 0;
                    }else
                    {
                        S[y, x] = 1.0 - 3.0 * min(R, G, B) / RGBSum;
                    }
                    //计算I
                    I[y, x] = (byte)(RGBSum / 3 + (RGBSum % 3) / 2);
                    palette[I[y, x]] += 1;
                }
            }
            //计算调色板
            double totalPixel = height * width;
            double colorSum = 0;
            for (int i = 0; i < 256; i++)
            {
                colorSum += palette[i];
                palette[i] = (int)(255 * (colorSum / totalPixel));
            }
            //填充颜色 HSI-RGB
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    double s = S[y, x];
                    int gray = palette[I[y, x]];
                    short h = H[y, x];

                    int R = 0;
                    int G = 0;
                    int B = 0;

                    if(h <= 0 || h > 360)
                    {
                        continue;
                    }

                    if (h <= 120)
                    {
                        B = (int)(gray * (1.0 - s));
                        R = (int)(gray *(1.0 + s * Math.Cos(h) / Math.Cos(60.0 - h)));
                        G = 3 * gray - (R + B);

                    }else if(h <= 240)
                    {
                        h = (short)(h - 120);
                        R = (int)(gray * (1.0 - s));
                        G = (int)(gray * (1.0 + s * Math.Cos(h) / Math.Cos(60.0 - h)));
                        B = 3 * gray - (R + G);

                    }else
                    {
                        h = (short)(h - 240);
                        G = (int)(gray * (1.0 - s));
                        B = (int)(gray * (1.0 + s * Math.Cos(h) / Math.Cos(60.0 - h)));
                        R = 3 * gray - (G + B);

                    }
                    if (R < 0)
                    {
                        R = 0;
                    }
                    if (G < 0)
                    {
                        G = 0;
                    }
                    if (B < 0)
                    {
                        B = 0;
                    }
                    if (R > 255)
                    {
                        R = 255;
                    }
                    if (G > 255)
                    {
                        G = 255;
                    }
                    if (B > 255)
                    {
                        B = 255;
                    }
                    bitmap.SetPixel(x, y, Color.FromArgb(R, G, B));
                }
            }
        }

        //增强彩色对比度(YCbCr)
        public void enhanceColorImageYcbcr()
        {
            int[,] Y = new int[height, width];
            int[] Ye = new int[256];
            double[,] Cb = new double[height, width];
            double[,] Cr = new double[height, width];
            for(int y = 0; y <height; y++)
            {
                for(int x = 0; x < width; x++)
                {
                    Color currColor = bitmap.GetPixel(x, y);
                    int R = currColor.R;
                    int G = currColor.G;
                    int B = currColor.B;
                    Y[y, x] = (int)((65.738 * R + 129.057 * G + 25.06 * B) / 256 + 16);
                    Ye[Y[y, x]] += 1;
                    Cb[y, x] = (-37.945 * R - 74.494 * G + 112.43 * B) / 256 + 128;
                    Cr[y, x] = (112.439 * R - 94.154 * G - 18.28 * B) / 256 + 128;
                }
            }
            int sum = 0;
            for(int i = 0; i < 256; i++)
            {
                sum += Ye[i];
                Ye[i] = (int)(255.0 * sum / (width * height));
            }

            for(int y = 0; y < height; y++)
            {
                for(int x = 0; x < width; x++)
                {
                    int yl = Ye[Y[y, x]];
                    double cb = Cb[y, x];
                    double cr = Cr[y, x];
                    int R = (int)((298.082 * (yl - 16) + 408.58 * (cr - 128)) / 256);
                    int G = (int)((298.082 * (yl - 16) - 100.291 * (cb - 128) - 208.12 * (cr - 128)) / 256);
                    int B = (int)((298.082 * (yl - 16) + 516.411 * (cb - 128)) / 256);
                    if (R < 0) R = 0;
                    if (R > 255) R = 255;
                    if (G < 0) G = 0;
                    if (G > 255) G = 255;
                    if (B < 0) B = 0;
                    if (B > 255) B = 255;
                    bitmap.SetPixel(x, y, Color.FromArgb(R, G, B));
                }
            }
        }

        int min(int R, int G, int B)
        {
            int minValue = R;
            if(G < minValue)
            {
                minValue = G;
            }
            if(B < minValue)
            {
                minValue = B;
            }
            return minValue;
        }

        //无损预测变换
        public void losslessPre()
        {
            int[,] foreMap = new int[height, width];
            for(int y = 0; y < height; y++)
            {
                int prevGrayValue = 0;
                int currGrayValue = bitmap.GetPixel(0, y).R;
                int foreGrayValue = (currGrayValue - prevGrayValue + 255) / 2;
                foreMap[y, 0] = foreGrayValue;
                prevGrayValue = currGrayValue;
                for(int x = 1; x < width; x++)
                {
                    currGrayValue = bitmap.GetPixel(x, y).R;
                    foreGrayValue = (currGrayValue - prevGrayValue + 255) / 2;
                    foreMap[y, x] = foreGrayValue;
                    prevGrayValue = currGrayValue;
                }
            }
            for(int y = 0; y < height; y++)
            {
                for(int x = 0; x < width; x++)
                {
                    int grayValue = foreMap[y, x];
                    bitmap.SetPixel(x, y, Color.FromArgb(grayValue, grayValue, grayValue));
                }
            }
        }

        //均匀量化变换
        public void uniformQuantization(int bitRemain)
        {
            if(bitRemain < 1 || bitRemain > 7)
            {
                return;
            }
            int divisor = 1 << (9 - bitRemain);
            int[,] foreMap = new int[height, width];
            int prevGrayValue = 0;
            int preHeadGrayValue = 0;
            for (int y = 0; y < height; y++)
            {
                int currGrayValue = bitmap.GetPixel(0, y).R;
                int stepValue;
                if(y != 0)
                {
                    prevGrayValue = preHeadGrayValue;
                    stepValue = currGrayValue - prevGrayValue;
                    foreMap[y, 0] = stepValue / divisor * divisor;
                    preHeadGrayValue += foreMap[y, 0];
                }
                else
                {
                    foreMap[y, 0] = currGrayValue;
                    preHeadGrayValue = currGrayValue;
                }
                prevGrayValue += foreMap[y, 0];
                for (int x = 1; x < width; x++)
                {
                    currGrayValue = bitmap.GetPixel(x, y).R;
                    stepValue = currGrayValue - prevGrayValue;
                    foreMap[y, x] = stepValue / divisor * divisor;
                    prevGrayValue += foreMap[y, x];
                }
            }
            int grayValue = 0;
            for (int y = 0; y < height; y++)
            {
                if(y == 0)
                {
                    grayValue = foreMap[y, 0];
                }else
                {
                    grayValue = bitmap.GetPixel(0, y - 1).R + foreMap[y, 0];
                }


                bitmap.SetPixel(0, y, Color.FromArgb(grayValue, grayValue, grayValue));
                for (int x = 1; x < width; x++)
                {
                    grayValue += foreMap[y, x];

                    bitmap.SetPixel(x, y, Color.FromArgb(grayValue, grayValue, grayValue));
                    
                }
            }
        }
        public void uniformQuantization1(int bitRemain)
        {
            if(bitRemain < 1 || bitRemain > 7)
            {
                return;
            }
            for(int y = 0; y < height; y++)
            {
                for(int x = 0; x < width; x++)
                {
                    Color currColor = bitmap.GetPixel(x, y);
                    int grayValue = (currColor.R + currColor.G + currColor.B) / 3;
                    int remainGrayValue = (grayValue >> (8 - bitRemain)) << (8 - bitRemain);
                    bitmap.SetPixel(x, y, Color.FromArgb(remainGrayValue, remainGrayValue, remainGrayValue));
                }
            }
        }
        //dct变换
        public void dct(int dim, int func)
        {
            if(bitmap == null)
            {
                return;
            }
            if(powerOf2(dim) == false)
            {
                return;
            }

            double[,] A = new double[dim, dim];
            double[,] AT = new double[dim, dim];
            for (int i = 0; i < dim; i++)
            {
                double a;
                if (i == 0)
                {
                    a = Math.Sqrt(1.0 / dim);
                }else
                {
                    a = Math.Sqrt(2.0 / dim);
                }
                    for (int j = 0; j < dim; j++)
                {
                    A[i, j] = a * Math.Cos(Math.PI * (j + 0.5) * i / dim);
                    AT[j, i] = A[i, j];
                }
            }
            double[,] img = new double[height, width];
            setDCTImg(dim, A, AT, img);
            if(func == 0)
            {
                setBitmap(img);
            }else if(func == 1)
            {
                setIDCTimg(dim, A, AT, img);
                setBitmap(img);
            }else if(func == 2)
            {
                for(int y = 0; y < height; y += dim)
                {
                    for(int x = 0; x < width; x += dim)
                    {
                        for (int n = 0; n < dim && n + y < height; n++)
                        {
                            for (int m = 0; m < dim && m + x < width; m++)
                            {
                                if(n >= (dim - dim / 4) || m >= (dim - dim / 4))
                                {
                                    img[n + y, m + x] = 0;
                                }else if( n >= dim / 2 && m >= dim / 2)
                                {
                                    img[n + y, m + x] = 0;
                                }
                            }
                        }

                    }
                }
                setIDCTimg(dim, A, AT, img);
                setBitmap(img);
            }
        }

        private void setDCTImg(int dim, double[,] A, double[,] AT, double[,] img)
        {
            double[,] partMatrix = new double[dim, dim];
            double[,] resultMatrix = new double[dim, dim];
            for(int y = 0; y < height; y += dim)
            {
                for(int x = 0; x < width; x += dim)
                {
                    //矩阵置零
                    setZero(resultMatrix, dim);
                    //分块赋值
                    for(int i = 0; i < dim && y + i < height; i++)
                    {
                        for(int j = 0; j < dim && x + j < width; j++)
                        {
                            resultMatrix[i, j] = bitmap.GetPixel(x + j, y + i).R;
                        }
                    }
                    //矩阵相乘计算结果
                    multMatrix(A, resultMatrix, partMatrix, dim);
                    multMatrix(partMatrix, AT, resultMatrix, dim);
                    //分块结果赋值
                    for (int i = 0; i < dim && y + i < height; i++)
                    {
                        for (int j = 0; j < dim && x + j < width; j++)
                        {
                            img[y + i, x + j] = resultMatrix[i, j];
                        }
                    }
                }
            }
        }
        private void setIDCTimg(int dim, double[,] A, double[,] AT, double[,] img)
        {
            double[,] partMatrix = new double[dim, dim];
            double[,] resultMatrix = new double[dim, dim];
            for (int y = 0; y < height; y += dim)
            {
                for (int x = 0; x < width; x += dim)
                {
                    //矩阵置零
                    setZero(resultMatrix, dim);
                    //分块赋值
                    for (int i = 0; i < dim && y + i < height; i++)
                    {
                        for (int j = 0; j < dim && x + j < width; j++)
                        {
                            resultMatrix[i, j] = img[y + i, x + j];
                        }
                    }
                    //矩阵相乘计算结果
                    multMatrix(AT, resultMatrix, partMatrix, dim);
                    multMatrix(partMatrix, A, resultMatrix, dim);
                    //分块结果赋值
                    for (int i = 0; i < dim && y + i < height; i++)
                    {
                        for (int j = 0; j < dim && x + j < width; j++)
                        {
                            img[y + i, x + j] = resultMatrix[i, j];
                        }
                    }
                }
            }
        }

        private void setBitmap(double[,] img)
        {
            double max = img[0, 0];
            double min = max;
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    double value = img[y, x];
                    if(value < min)
                    {
                        min = value;
                    }else if(value > max)
                    {
                        max = value;
                    }
                }
            }
            double Range = max - min;
            for (int y = 0; y < height; y++)
            {
                for(int x = 0; x < width; x++)
                {
                    int value = (int)((img[y, x] - min) * 255 / Range);
                    bitmap.SetPixel(x, y, Color.FromArgb(value, value, value));
                }
            }
        }

        private void setZero(double[,] m, int dim)
        {
            for(int i = 0; i < dim; i++)
            {
                for(int j = 0; j < dim; j++)
                {
                    m[i, j] = 0;
                }
            }
        }

        private void multMatrix(double[,] M1, double[,] M2, double[,] dst, int dim)
        {
            for(int i = 0; i < dim; i++)
            {
                for(int j = 0; j < dim; j++)
                {
                    double value = 0;
                    for(int n = 0; n < dim; n++)
                    {
                        value += M1[i, n] * M2[n, j]; 
                    }
                    dst[i, j] = value;
                }
            }
        }

        //生成dither矩阵
        private void setDitherMatrix(int[,] matrix, int dim, int startX, int startY)
        {
            if(dim < 2)
            {
                return;
            }

            if(dim == 2)
            {
                matrix[startX, startY] = 0;
                matrix[startX, startY + 1] = 2;
                matrix[startX + 1, startY] = 3;
                matrix[startX + 1, startY + 1] = 1;
                return;
            }
            int nextDim = dim / 2;
            for(int i = 0; i < 2; i++)
            {
                for(int j = 0; j < 2; j++)
                {
                    int oneValue = OneMatrixValue[i, j];
                    int currStartX = startX + i * nextDim;
                    int currStartY = startY + j * nextDim;
                    //每4块递归执行D(n/2)
                    setDitherMatrix(matrix, nextDim, currStartX, currStartY);
                    //4块各加上0,2,3,1
                    for(int m = currStartX; m < currStartX + nextDim; m++)
                    {
                        for(int n = currStartY; n < currStartY + nextDim; n++)
                        {
                            matrix[m, n] = 4 * matrix[m, n] + oneValue;
                        }
                    }
                }
            }

            return;
        }

        //判断dim是否是2的倍数
        public bool powerOf2(int dim)
        {
            if (dim <= 0)
            {
                return false;
            }
            int count = 0;
            for (int i = 0; i < 32; i++)
            {
                int bit = (dim >> i) & 1;
                if (bit == 1)
                {
                    count++;
                }
            }

            if (count != 1)
            {
                return false;
            }
            return true;
        }
    }
}
