using System.Drawing;
using System.IO;

namespace SimplePS
{
    class OpBitmap
    {
        //原始图像
        public Bitmap currBitmap = null;
        //操作后的图像
        public Bitmap opratedBitmap = null;
        //图像绘制类
        BitmapDrawer bitmapDrawer = null;

        //文件头
        byte[] fileHead = new byte[14];
        //bmp信息头
        byte[] bitInfoHead = new byte[40];
        //调色板
        byte[] bytePalette = null;
        //转为Color类的调色板
        Color[] palette = null;
        //当前图像类型
        BitModel currBitmodel;

        public OpBitmap()
        {
            bitmapDrawer = new BitmapDrawer();
        }

        //读取bmp文件函数
        public bool getBitmap(string path)
        {
            //判断文件存在
            if (File.Exists(path) == false)
            {
                return false;
            }

            using (FileStream fileStream = new FileStream(path, FileMode.Open, FileAccess.Read))
            {
                int readBytesNum = 0;
                //读取文件头
                int readNum = 0;
                readNum = fileStream.Read(fileHead, 0, fileHead.Length);
                if (readNum != 14)
                {
                    return false;
                }
                readBytesNum += 14;

                //读取位图信息头
                readNum = fileStream.Read(bitInfoHead, 0, bitInfoHead.Length);
                if (readNum != 40)
                {
                    return false;
                }
                readBytesNum += 40;

                //读取文件类型
                if (fileHead[0] != 'B' || fileHead[1] != 'M')
                {
                    return false;
                }
                //读取到图像数据的偏移
                int headOffset = 0;
                for (int i = 10; i < 14; i++)
                {
                    headOffset += fileHead[i] << ((i - 10) * 8);
                }
                //读取宽度
                int width = 0;
                for (int i = 4; i < 8; i++)
                {
                    width += (bitInfoHead[i]) << ((i - 4) * 8);
                }
                //读取高度
                int height = 0;
                for (int i = 8; i < 12; i++)
                {
                    height += (bitInfoHead[i]) << ((i - 12) * 8);
                }
                //读取位深
                int bitCount = 0;
                bitCount += (bitInfoHead[15] << 8) + bitInfoHead[14];


                //行容量
                int rawByteCount = 0;
                //申请调色板
                if (bitCount == 1 || bitCount == 4 || bitCount == 8)
                {
                    palette = new Color[1 << bitCount];
                    bytePalette = new byte[palette.Length * 4];
                    readNum = fileStream.Read(bytePalette, 0, bytePalette.Length);
                    if (readNum != bytePalette.Length)
                    {
                        return false;
                    }
                    readBytesNum += bytePalette.Length;

                    for (int i = 0; i < palette.Length; i++)
                    {
                        int cellOffset = i * 4;
                        palette[i] = Color.FromArgb(bytePalette[cellOffset + 2]
                                                , bytePalette[cellOffset + 1]
                                                , bytePalette[cellOffset]);
                    }

                    //计算行容量
                    int byteCount = (bitCount * width + (8 - bitCount)) / 8;
                    rawByteCount = ((byteCount + 3) / 4) * 4;

                }else if(bitCount == 24)
                {
                    int byteCount = 3 * width;
                    rawByteCount = ((byteCount + 3) / 4) * 4;
                }
                else if (bitCount == 32)
                {
                    int byteCount = width * 4;
                    rawByteCount = byteCount;
                }
                else
                {
                    return false;
                }

                if(currBitmap != null)
                {
                    currBitmap.Dispose();
                }

                currBitmap = new Bitmap(width, height);

                //读取图像数据
                byte[] rawByte = new byte[rawByteCount];
                //位深为1
                if (bitCount == 1)
                {
                    int cellCount = (width) / 8;
                    int bitLeft = width % 8;

                    for (int j = height - 1; j >= 0; j--)
                    {
                        //读取一行
                        readNum = fileStream.Read(rawByte, 0, rawByteCount);
                        readBytesNum += readNum;
                        if (rawByteCount != readNum)
                        {
                            return false;
                        }

                        //当前x
                        int i = 0;

                        for (int cellIndex = 0; cellIndex < cellCount; cellIndex++)
                        {
                            byte currByte = rawByte[cellIndex];
                            for (int n = 7; n >= 0 && i < width; n--)
                            {
                                currBitmap.SetPixel(i, j, palette[(currByte >> n) & 1]);
                                i++;
                            }
                        }

                        //剩余bit
                        if (i < width)
                        {
                            byte currByte = rawByte[cellCount];
                            for (int n = 7; n >= (8 - bitLeft); n--)
                            {
                                currBitmap.SetPixel(i, j, palette[(currByte >> n) & 1]);
                                i++;
                            }
                        }
                    }

                    currBitmodel = BitModel.BW;
                }
                //位深为4
                else if (bitCount == 4)
                {
                    int cellCount = (width) / 2;
                    int bitLeft = width % 2;

                    for (int j = height - 1; j >= 0; j--)
                    {
                        //读取一行
                        readNum = fileStream.Read(rawByte, 0, rawByteCount);
                        readBytesNum += readNum;
                        if (rawByteCount != readNum)
                        {
                            return false;
                        }

                        //当前x
                        int i = 0;

                        for (int cellIndex = 0; cellIndex < cellCount; cellCount++)
                        {
                            byte currByte = rawByte[cellIndex];
                            for (int n = 4; n >= 0; n -= 4)
                            {
                                currBitmap.SetPixel(i, j, palette[(currByte >> n) & 0xF]);
                                i++;
                            }
                        }

                        //剩余bit
                        if (i < width)
                        {
                            byte currByte = rawByte[cellCount];
                            for (int n = 4; n >= (2 - bitLeft); n -= 4)
                            {
                                currBitmap.SetPixel(i, j, palette[(currByte >> n) & 0xF]);
                                i++;
                            }
                        }
                    }

                    currBitmodel = BitModel.COLOR_4;
                }
                //位深为8
                else if (bitCount == 8)
                {
                    for (int j = height - 1; j >= 0; j--)
                    {
                        //读取一行
                        readNum = fileStream.Read(rawByte, 0, rawByteCount);
                        readBytesNum += readNum;
                        if (rawByteCount != readNum)
                        {
                            return false;
                        }

                        for (int i = 0; i < width; i++)
                        {
                            currBitmap.SetPixel(i, j, palette[rawByte[i]]);
                        }
                    }
                    currBitmodel = BitModel.COLOR_8;
                }
                //位深为24或32
                else if (bitCount == 32 || bitCount == 24)
                {
                    int step = bitCount / 8;
                    for (int j = height - 1; j >= 0; j--)
                    {
                        //读取一行
                        readNum = fileStream.Read(rawByte, 0, rawByteCount);
                        readBytesNum += readNum;
                        if (rawByteCount != readNum)
                        {
                            return false;
                        }

                        int i = 0;
                        int currIndex = 0;

                        for (; i < width; i++)
                        {
                            currBitmap.SetPixel(i, j, Color.FromArgb(rawByte[currIndex + 2], rawByte[currIndex + 1], rawByte[currIndex]));
                            currIndex += step;
                        }
                    }
                }

                currBitmodel = BitModel.COLOR_24_32;
            }

            if(opratedBitmap != null)
            {
                opratedBitmap.Dispose();
            }

            opratedBitmap = new Bitmap(currBitmap);

            return true;
        }

        public void setSize(int width, int height)
        {
            //设置宽度
            for (int i = 4; i < 8; i++)
            {
                bitInfoHead[i] = (byte)((width >> ((i - 4) * 8)) & 0xFF);
            }
            //设置长度
            for (int i = 8; i < 12; i++)
            {
                bitInfoHead[i] = (byte)((height >> ((i - 8) * 8)) & 0xFF);
            }
        }
        public int getWidth()
        {
            int width = 0;
            for (int i = 4; i < 8; i++)
            {
                width += (bitInfoHead[i]) << ((i - 4) * 8);
            }
            return width;
        }
        public int getHeight()
        {
            //读取高度
            int height = 0;
            for (int i = 8; i < 12; i++)
            {
                height += (bitInfoHead[i]) << ((i - 12) * 8);
            }
            return height;
        }

        private void setPalette(BitModel bitModel)
        {
            bytePalette = null;

            if (bitModel == BitModel.COLOR_24_32)
            {
                setHeadOffset(54);

                bitInfoHead[14] = 32;
                bitInfoHead[15] = 0;
            }
            else if (bitModel == BitModel.GRAY)
            {
                bytePalette = new byte[256 * 4];
                int currIndex = 0;
                for (int i = 0; i < 256; i++)
                {
                    bytePalette[currIndex] = (byte)i;
                    bytePalette[currIndex + 1] = (byte)i;
                    bytePalette[currIndex + 2] = (byte)i;
                    bytePalette[currIndex + 3] = 0;
                    currIndex += 4;
                }

                setHeadOffset(1078);

                bitInfoHead[14] = 8;
                bitInfoHead[15] = 0;

            }
            else if (bitModel == BitModel.BW)
            {
                bytePalette = new byte[8];
                for (int i = 0; i < 4; i++)
                {
                    bytePalette[i] = 0;
                }
                for (int i = 4; i < 7; i++)
                {
                    bytePalette[i] = 255;
                }
                bytePalette[7] = 0;

                setHeadOffset(70);

                bitInfoHead[14] = 1;
                bitInfoHead[15] = 0;
            }
        }

        public void saveBitmap(string path)
        {
            if (path == null || path == "" || opratedBitmap == null)
            {
                return;
            }
            /*
            if (currBitmodel == BitModel.BW)
            {
                currBitmodel = BitModel.GRAY;
                setPalette(BitModel.GRAY);
            }
            */
            using (FileStream fileStream = new FileStream(path, FileMode.Create, FileAccess.Write))
            {
                int lineLength = 0;
                int imgWidth = opratedBitmap.Width;
                int imgHeight = opratedBitmap.Height;
                int biSizeImage = 0;
                int bfSize = 0;

                if (currBitmodel == BitModel.COLOR_24_32)
                {
                    lineLength = imgWidth * 4;
                }
                else if (currBitmodel == BitModel.GRAY)
                {
                    lineLength = (imgWidth + 3) / 4 * 4;
                }
                else if (currBitmodel == BitModel.BW)
                {
                    lineLength = ((imgWidth + 7) / 8 + 3) / 4 * 4;
                }

                biSizeImage = lineLength * imgHeight;
                bfSize = 54 + biSizeImage;
                if (bytePalette != null)
                {
                    bfSize += bytePalette.Length;
                }
                for (int i = 2; i < 6; i++)
                {
                    fileHead[i] = (byte)((bfSize >> (i - 2)) & 0xFF);
                }
                for (int i = 20; i < 24; i++)
                {
                    bitInfoHead[i] = (byte)((biSizeImage >> (i - 20)) & 0xFF);
                }

                fileStream.Write(fileHead, 0, fileHead.Length);
                fileStream.Write(bitInfoHead, 0, bitInfoHead.Length);
                if (bytePalette != null)
                {
                    fileStream.Write(bytePalette, 0, bytePalette.Length);
                }
                byte[] byteLine = null;
                if (currBitmodel == BitModel.COLOR_24_32)
                {
                    lineLength = imgWidth * 4;
                    byteLine = new byte[lineLength];
                    for (int i = 0; i < lineLength; i++)
                    {
                        byteLine[i] = 0;
                    }
                    for (int i = imgHeight - 1; i >= 0; i--)
                    {
                        int xIndex = 0;
                        for (int j = 0; j < imgWidth; j++)
                        {
                            Color currColor = opratedBitmap.GetPixel(j, i);
                            byteLine[xIndex] = currColor.B;
                            byteLine[xIndex + 1] = currColor.G;
                            byteLine[xIndex + 2] = currColor.R;
                            byteLine[xIndex + 3] = currColor.A;
                            xIndex += 4;
                        }
                        fileStream.Write(byteLine, 0, lineLength);
                    }
                }
                else if (currBitmodel == BitModel.GRAY)
                {
                    lineLength = (imgWidth + 3) / 4 * 4;
                    byteLine = new byte[lineLength];
                    for (int i = imgHeight - 1; i >= 0; i--)
                    {
                        for (int j = 0; j < imgWidth; j++)
                        {
                            Color currColor = opratedBitmap.GetPixel(j, i);
                            byteLine[j] = currColor.R;
                        }
                        fileStream.Write(byteLine, 0, lineLength);
                    }
                }
                else if (currBitmodel == BitModel.BW)
                {
                    lineLength = ((imgWidth + 7) / 8 + 3) / 4 * 4;
                    byteLine = new byte[lineLength];
                    for (int i = imgHeight - 1; i >= 0; i--)
                    {
                        for (int j = 0; j < imgWidth; j++)
                        {
                            int cell = j / 8;
                            int offset = 7 - (j % 8);
                            Color currColor = opratedBitmap.GetPixel(j, i);
                            if (currColor.R > 127)
                            {
                                byteLine[cell] = (byte)(byteLine[cell] | (1 << offset));
                            }
                            else
                            {
                                byteLine[cell] = (byte)(byteLine[cell] & (~(1 << offset)));
                            }
                        }
                        fileStream.Write(byteLine, 0, lineLength);
                    }
                }

                byteLine = null;
            }
        }

        private void setHeadOffset(int headOffset)
        {
            //设置到图像数据的偏移
            for (int i = 10; i < 14; i++)
            {
                fileHead[i] = (byte)((headOffset >> ((i - 10) * 8)) & 0xFF);
            }
        }

        //转为黑白图像
        public bool turnBWImage(int threshold)
        {
            if (currBitmap == null)
            {
                return false;
            }
            if (opratedBitmap != null)
            {
                opratedBitmap.Dispose();
            }
            opratedBitmap = new Bitmap(currBitmap);

            setPalette(BitModel.BW);
            currBitmodel = BitModel.BW;

            bitmapDrawer.setBitmap(opratedBitmap);
            bitmapDrawer.turnBWImage1_1(threshold);
            return true;
        }

        //转为灰度图像
        public bool turnGrayImage()
        {
            if (currBitmap == null)
            {
                return false;
            }
            if (opratedBitmap != null)
            {
                opratedBitmap.Dispose();
            }
            opratedBitmap = new Bitmap(currBitmap);

            setPalette(BitModel.GRAY);
            currBitmodel = BitModel.GRAY;
            bitmapDrawer.setBitmap(opratedBitmap);
            bitmapDrawer.turnGrayImage();
            return true;
        }

        //增强灰度图像对比度
        public bool enhanceGrayImage()
        {
            if (currBitmap == null)
            {
                return false;
            }
            if (opratedBitmap != null)
            {
                opratedBitmap.Dispose();
            }
            opratedBitmap = new Bitmap(currBitmap);

            setPalette(BitModel.GRAY);
            currBitmodel = BitModel.GRAY;
            bitmapDrawer.setBitmap(opratedBitmap);
            bitmapDrawer.enhanceImage();
            return true;
        }

        //dither操作
        public bool dither(int dim)
        {
            if(currBitmap == null)
            {
                return false;
            }

            if(dim > 256)
            {
                return false;
            }
            if(powerOf2(dim) == false)
            {
                return false;
            }
            if (opratedBitmap != null)
            {
                opratedBitmap.Dispose();
            }
            opratedBitmap = new Bitmap(currBitmap);

            setPalette(BitModel.BW);
            currBitmodel = BitModel.BW;
            bitmapDrawer.setBitmap(opratedBitmap);
            bitmapDrawer.turnBWImage1_x(dim);
            return true;
        }

        //orderDither操作
        public bool orderDither(int dim)
        {
            if(opratedBitmap == null || currBitmap == null)
            {
                return false;
            }

            if (dim > 16)
            {
                return false;
            }
            if (powerOf2(dim) == false)
            {
                return false;
            }
            if (opratedBitmap != null)
            {
                opratedBitmap.Dispose();
            }
            opratedBitmap = new Bitmap(currBitmap.Width * dim, currBitmap.Height * dim);

            setPalette(BitModel.BW);
            currBitmodel = BitModel.BW;
            setSize(opratedBitmap.Width, opratedBitmap.Height);

            bitmapDrawer.setBitmap(opratedBitmap);
            bitmapDrawer.turnBWImageX(currBitmap, dim);
            return true;
        }

        //增强彩色对比度
        public bool enhanceColorImage()
        {
            if (currBitmap == null)
            {
                return false;
            }
            if (opratedBitmap != null)
            {
                opratedBitmap.Dispose();
            }
            opratedBitmap = new Bitmap(currBitmap);

            setPalette(BitModel.COLOR_24_32);
            currBitmodel = BitModel.COLOR_24_32;
            bitmapDrawer.setBitmap(opratedBitmap);
            bitmapDrawer.enhanceColorImageYcbcr();

            return true;
        }

        //无损预测
        public bool losslessPre()
        {
            if (currBitmap == null)
            {
                return false;
            }
            if (opratedBitmap != null)
            {
                opratedBitmap.Dispose();
            }

            if (currBitmodel != BitModel.GRAY)
            {
                turnGrayImage();
            }else
            {
                opratedBitmap = new Bitmap(currBitmap);
            }
            
            bitmapDrawer.setBitmap(opratedBitmap);
            bitmapDrawer.losslessPre();

            return true;
        }

        //均匀量化
        public bool uniformQuatization(int bitRemain)
        {
            if (currBitmap == null)
            {
                return false;
            }
            if (opratedBitmap != null)
            {
                opratedBitmap.Dispose();
            }

            if (currBitmodel != BitModel.GRAY)
            {
                turnGrayImage();
            }
            else
            {
                opratedBitmap = new Bitmap(currBitmap);
            }

            bitmapDrawer.setBitmap(opratedBitmap);
            bitmapDrawer.uniformQuantization1(bitRemain);

            return true;
        }

        //dct变换
        public bool dct(int dim, int func)
        {
            if (currBitmap == null)
            {
                return false;
            }
            if(powerOf2(dim) == false || dim < 2 || dim > 64 || func < 0 || func > 2)
            {
                return false;
            }
            if (opratedBitmap != null)
            {
                opratedBitmap.Dispose();
            }

            if (currBitmodel != BitModel.GRAY)
            {
                turnGrayImage();
            }
            else
            {
                opratedBitmap = new Bitmap(currBitmap);
            }

            bitmapDrawer.setBitmap(opratedBitmap);
            bitmapDrawer.dct(dim, func);

            return true;
        }

        //判断dim是否是2的倍数
        public bool powerOf2(int dim)
        {
            if(dim <= 0)
            {
                return false;
            }
            int count = 0;
            for(int i = 0; i < 32; i++)
            {
                int bit = (dim >> i) & 1;
                if(bit == 1)
                {
                    count++;
                }
            }

            if(count != 1)
            {
                return false;
            }
            return true;
        }

        public void dispose()
        {
            if (currBitmap != null)
            {
                currBitmap.Dispose();
                currBitmap = null;
            }
            if(opratedBitmap != null)
            {
                opratedBitmap.Dispose();
                opratedBitmap = null;
            }
        }
    }



    enum BitModel
    {
        BW,
        GRAY,
        COLOR_4,
        COLOR_8,
        COLOR_24_32,
    }
}
