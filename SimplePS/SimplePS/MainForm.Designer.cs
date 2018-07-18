namespace SimplePS
{
    partial class MainForm
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                //释放图片资源
                opBitmap.dispose();

                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要修改
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.FileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.OpenFileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.SaveToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.SaveAsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.OprateToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.TurnToGrayImageItem = new System.Windows.Forms.ToolStripMenuItem();
            this.EnhanceItem = new System.Windows.Forms.ToolStripMenuItem();
            this.TurnToBWImage = new System.Windows.Forms.ToolStripMenuItem();
            this.阈值ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.thresholdTextBox = new System.Windows.Forms.ToolStripTextBox();
            this.ditherToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.ditherTextBox = new System.Windows.Forms.ToolStripTextBox();
            this.orderDitherToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.orderedDitherTextBox = new System.Windows.Forms.ToolStripTextBox();
            this.EnhanceColorMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.LosslessPreItem = new System.Windows.Forms.ToolStripMenuItem();
            this.UniformQuatizationItem = new System.Windows.Forms.ToolStripMenuItem();
            this.compRatioTextBox = new System.Windows.Forms.ToolStripTextBox();
            this.dCTToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.DCTItem = new System.Windows.Forms.ToolStripMenuItem();
            this.DctDimTextBox = new System.Windows.Forms.ToolStripTextBox();
            this.IDCTItem = new System.Windows.Forms.ToolStripMenuItem();
            this.IDctDimTextBox = new System.Windows.Forms.ToolStripTextBox();
            this.HalfIDCTItem = new System.Windows.Forms.ToolStripMenuItem();
            this.HalfIDctDimTextBox = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.RollbackItem = new System.Windows.Forms.ToolStripMenuItem();
            this.mainPanel = new SimplePS.MainPanel();
            this.pictureBox = new System.Windows.Forms.PictureBox();
            this.menuStrip1.SuspendLayout();
            this.mainPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox)).BeginInit();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            this.menuStrip1.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.FileToolStripMenuItem,
            this.OprateToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(1075, 28);
            this.menuStrip1.TabIndex = 1;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // FileToolStripMenuItem
            // 
            this.FileToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.OpenFileToolStripMenuItem,
            this.SaveToolStripMenuItem,
            this.SaveAsToolStripMenuItem});
            this.FileToolStripMenuItem.Name = "FileToolStripMenuItem";
            this.FileToolStripMenuItem.Size = new System.Drawing.Size(51, 24);
            this.FileToolStripMenuItem.Text = "文件";
            // 
            // OpenFileToolStripMenuItem
            // 
            this.OpenFileToolStripMenuItem.Name = "OpenFileToolStripMenuItem";
            this.OpenFileToolStripMenuItem.Size = new System.Drawing.Size(129, 26);
            this.OpenFileToolStripMenuItem.Text = "打开";
            this.OpenFileToolStripMenuItem.Click += new System.EventHandler(this.OpenFileToolStripMenuItem_Click);
            // 
            // SaveToolStripMenuItem
            // 
            this.SaveToolStripMenuItem.Name = "SaveToolStripMenuItem";
            this.SaveToolStripMenuItem.Size = new System.Drawing.Size(129, 26);
            this.SaveToolStripMenuItem.Text = "保存";
            this.SaveToolStripMenuItem.Click += new System.EventHandler(this.SaveToolStripMenuItem_Click);
            // 
            // SaveAsToolStripMenuItem
            // 
            this.SaveAsToolStripMenuItem.Name = "SaveAsToolStripMenuItem";
            this.SaveAsToolStripMenuItem.Size = new System.Drawing.Size(129, 26);
            this.SaveAsToolStripMenuItem.Text = "另存为";
            this.SaveAsToolStripMenuItem.Click += new System.EventHandler(this.SaveAsToolStripMenuItem_Click);
            // 
            // OprateToolStripMenuItem
            // 
            this.OprateToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.TurnToGrayImageItem,
            this.EnhanceItem,
            this.TurnToBWImage,
            this.EnhanceColorMenuItem,
            this.LosslessPreItem,
            this.UniformQuatizationItem,
            this.dCTToolStripMenuItem,
            this.toolStripSeparator1,
            this.RollbackItem});
            this.OprateToolStripMenuItem.Name = "OprateToolStripMenuItem";
            this.OprateToolStripMenuItem.Size = new System.Drawing.Size(51, 24);
            this.OprateToolStripMenuItem.Text = "操作";
            // 
            // TurnToGrayImageItem
            // 
            this.TurnToGrayImageItem.Name = "TurnToGrayImageItem";
            this.TurnToGrayImageItem.Size = new System.Drawing.Size(189, 26);
            this.TurnToGrayImageItem.Text = "转为灰度图像";
            this.TurnToGrayImageItem.Click += new System.EventHandler(this.TurnToGrayImageItem_Click);
            // 
            // EnhanceItem
            // 
            this.EnhanceItem.Name = "EnhanceItem";
            this.EnhanceItem.Size = new System.Drawing.Size(189, 26);
            this.EnhanceItem.Text = "增强灰度对比度";
            this.EnhanceItem.Click += new System.EventHandler(this.EnhanceItem_Click);
            // 
            // TurnToBWImage
            // 
            this.TurnToBWImage.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.阈值ToolStripMenuItem,
            this.ditherToolStripMenuItem,
            this.orderDitherToolStripMenuItem});
            this.TurnToBWImage.Name = "TurnToBWImage";
            this.TurnToBWImage.Size = new System.Drawing.Size(189, 26);
            this.TurnToBWImage.Text = "转为黑白图像";
            // 
            // 阈值ToolStripMenuItem
            // 
            this.阈值ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.thresholdTextBox});
            this.阈值ToolStripMenuItem.Name = "阈值ToolStripMenuItem";
            this.阈值ToolStripMenuItem.Size = new System.Drawing.Size(173, 26);
            this.阈值ToolStripMenuItem.Text = "阈值";
            // 
            // thresholdTextBox
            // 
            this.thresholdTextBox.Name = "thresholdTextBox";
            this.thresholdTextBox.Size = new System.Drawing.Size(100, 27);
            this.thresholdTextBox.Text = "128";
            this.thresholdTextBox.KeyDown += new System.Windows.Forms.KeyEventHandler(this.thresholdTextBox_KeyDown);
            // 
            // ditherToolStripMenuItem
            // 
            this.ditherToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.ditherTextBox});
            this.ditherToolStripMenuItem.Name = "ditherToolStripMenuItem";
            this.ditherToolStripMenuItem.Size = new System.Drawing.Size(173, 26);
            this.ditherToolStripMenuItem.Text = "dither";
            // 
            // ditherTextBox
            // 
            this.ditherTextBox.Name = "ditherTextBox";
            this.ditherTextBox.Size = new System.Drawing.Size(181, 27);
            this.ditherTextBox.Text = "2";
            this.ditherTextBox.KeyDown += new System.Windows.Forms.KeyEventHandler(this.ditherTextBox_KeyDown);
            // 
            // orderDitherToolStripMenuItem
            // 
            this.orderDitherToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.orderedDitherTextBox});
            this.orderDitherToolStripMenuItem.Name = "orderDitherToolStripMenuItem";
            this.orderDitherToolStripMenuItem.Size = new System.Drawing.Size(173, 26);
            this.orderDitherToolStripMenuItem.Text = "order dither";
            // 
            // orderedDitherTextBox
            // 
            this.orderedDitherTextBox.Name = "orderedDitherTextBox";
            this.orderedDitherTextBox.Size = new System.Drawing.Size(181, 27);
            this.orderedDitherTextBox.Text = "2";
            this.orderedDitherTextBox.KeyDown += new System.Windows.Forms.KeyEventHandler(this.orderedDitherTextBox_KeyDown);
            // 
            // EnhanceColorMenuItem
            // 
            this.EnhanceColorMenuItem.Name = "EnhanceColorMenuItem";
            this.EnhanceColorMenuItem.Size = new System.Drawing.Size(189, 26);
            this.EnhanceColorMenuItem.Text = "增强对比度";
            this.EnhanceColorMenuItem.Click += new System.EventHandler(this.EnhanceColorMenuItem_Click);
            // 
            // LosslessPreItem
            // 
            this.LosslessPreItem.Name = "LosslessPreItem";
            this.LosslessPreItem.Size = new System.Drawing.Size(189, 26);
            this.LosslessPreItem.Text = "无损预测处理";
            this.LosslessPreItem.Click += new System.EventHandler(this.LosslessPreItem_Click);
            // 
            // UniformQuatizationItem
            // 
            this.UniformQuatizationItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.compRatioTextBox});
            this.UniformQuatizationItem.Name = "UniformQuatizationItem";
            this.UniformQuatizationItem.Size = new System.Drawing.Size(189, 26);
            this.UniformQuatizationItem.Text = "均匀量化处理";
            // 
            // compRatioTextBox
            // 
            this.compRatioTextBox.Name = "compRatioTextBox";
            this.compRatioTextBox.Size = new System.Drawing.Size(181, 27);
            this.compRatioTextBox.Text = "1";
            this.compRatioTextBox.KeyDown += new System.Windows.Forms.KeyEventHandler(this.compRatioToolStripMenuItem_KeyDown);
            // 
            // dCTToolStripMenuItem
            // 
            this.dCTToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.DCTItem,
            this.IDCTItem,
            this.HalfIDCTItem});
            this.dCTToolStripMenuItem.Name = "dCTToolStripMenuItem";
            this.dCTToolStripMenuItem.Size = new System.Drawing.Size(189, 26);
            this.dCTToolStripMenuItem.Text = "DCT";
            // 
            // DCTItem
            // 
            this.DCTItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.DctDimTextBox});
            this.DCTItem.Name = "DCTItem";
            this.DCTItem.Size = new System.Drawing.Size(181, 26);
            this.DCTItem.Text = "dct变换";
            // 
            // DctDimTextBox
            // 
            this.DctDimTextBox.Name = "DctDimTextBox";
            this.DctDimTextBox.Size = new System.Drawing.Size(181, 27);
            this.DctDimTextBox.Text = "2";
            this.DctDimTextBox.KeyDown += new System.Windows.Forms.KeyEventHandler(this.DctDimTextBox_KeyDown);
            // 
            // IDCTItem
            // 
            this.IDCTItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.IDctDimTextBox});
            this.IDCTItem.Name = "IDCTItem";
            this.IDCTItem.Size = new System.Drawing.Size(181, 26);
            this.IDCTItem.Text = "dct还原";
            // 
            // IDctDimTextBox
            // 
            this.IDctDimTextBox.Name = "IDctDimTextBox";
            this.IDctDimTextBox.Size = new System.Drawing.Size(181, 27);
            this.IDctDimTextBox.Text = "2";
            this.IDctDimTextBox.KeyDown += new System.Windows.Forms.KeyEventHandler(this.IDctDimTextBox_KeyDown);
            // 
            // HalfIDCTItem
            // 
            this.HalfIDCTItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.HalfIDctDimTextBox});
            this.HalfIDCTItem.Name = "HalfIDCTItem";
            this.HalfIDCTItem.Size = new System.Drawing.Size(181, 26);
            this.HalfIDCTItem.Text = "50%dct还原";
            // 
            // HalfIDctDimTextBox
            // 
            this.HalfIDctDimTextBox.Name = "HalfIDctDimTextBox";
            this.HalfIDctDimTextBox.Size = new System.Drawing.Size(181, 27);
            this.HalfIDctDimTextBox.Text = "2";
            this.HalfIDctDimTextBox.KeyDown += new System.Windows.Forms.KeyEventHandler(this.HalfIDctDimTextBox_KeyDown);
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(186, 6);
            // 
            // RollbackItem
            // 
            this.RollbackItem.Name = "RollbackItem";
            this.RollbackItem.Size = new System.Drawing.Size(189, 26);
            this.RollbackItem.Text = "还原";
            this.RollbackItem.Click += new System.EventHandler(this.RollbackItem_Click);
            // 
            // mainPanel
            // 
            this.mainPanel.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.mainPanel.AutoScroll = true;
            this.mainPanel.BackColor = System.Drawing.SystemColors.ControlDarkDark;
            this.mainPanel.Controls.Add(this.pictureBox);
            this.mainPanel.Location = new System.Drawing.Point(2, 40);
            this.mainPanel.Name = "mainPanel";
            this.mainPanel.Size = new System.Drawing.Size(1061, 558);
            this.mainPanel.TabIndex = 0;
            // 
            // pictureBox
            // 
            this.pictureBox.Location = new System.Drawing.Point(3, 3);
            this.pictureBox.Name = "pictureBox";
            this.pictureBox.Size = new System.Drawing.Size(745, 397);
            this.pictureBox.TabIndex = 0;
            this.pictureBox.TabStop = false;
            this.pictureBox.Click += new System.EventHandler(this.pictureBox_Click);
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoScroll = true;
            this.BackColor = System.Drawing.SystemColors.ControlDarkDark;
            this.ClientSize = new System.Drawing.Size(1075, 610);
            this.Controls.Add(this.mainPanel);
            this.Controls.Add(this.menuStrip1);
            this.MainMenuStrip = this.menuStrip1;
            this.Name = "MainForm";
            this.Text = "SimplePS";
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.mainPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private MainPanel mainPanel;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem FileToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem OpenFileToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem SaveToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem SaveAsToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem OprateToolStripMenuItem;
        private System.Windows.Forms.PictureBox pictureBox;
        private System.Windows.Forms.ToolStripMenuItem TurnToGrayImageItem;
        private System.Windows.Forms.ToolStripMenuItem EnhanceItem;
        private System.Windows.Forms.ToolStripMenuItem TurnToBWImage;
        private System.Windows.Forms.ToolStripMenuItem RollbackItem;
        private System.Windows.Forms.ToolStripMenuItem EnhanceColorMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 阈值ToolStripMenuItem;
        private System.Windows.Forms.ToolStripTextBox thresholdTextBox;
        private System.Windows.Forms.ToolStripMenuItem ditherToolStripMenuItem;
        private System.Windows.Forms.ToolStripTextBox ditherTextBox;
        private System.Windows.Forms.ToolStripMenuItem orderDitherToolStripMenuItem;
        private System.Windows.Forms.ToolStripTextBox orderedDitherTextBox;
        private System.Windows.Forms.ToolStripMenuItem LosslessPreItem;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripMenuItem UniformQuatizationItem;
        private System.Windows.Forms.ToolStripTextBox compRatioTextBox;
        private System.Windows.Forms.ToolStripMenuItem dCTToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem DCTItem;
        private System.Windows.Forms.ToolStripTextBox DctDimTextBox;
        private System.Windows.Forms.ToolStripMenuItem IDCTItem;
        private System.Windows.Forms.ToolStripTextBox IDctDimTextBox;
        private System.Windows.Forms.ToolStripMenuItem HalfIDCTItem;
        private System.Windows.Forms.ToolStripTextBox HalfIDctDimTextBox;
    }
}

