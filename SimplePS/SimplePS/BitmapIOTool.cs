using System.Drawing;
using System.IO;

namespace SimplePS
{
    class BitmapIOTool
    {
        public Bitmap getBitmap(string path)
        {
            if(File.Exists(path) == false)
            {
                return null;
            }

            FileStream fileStream = new FileStream(path, FileMode.Open);
            

            return null;
        }
    }
}
