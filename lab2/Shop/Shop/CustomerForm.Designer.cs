namespace Shop
{
    partial class CustomerForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.usernameLabel = new System.Windows.Forms.Label();
            this.ordersListView = new System.Windows.Forms.ListView();
            this.columnHeader3 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader2 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.productsListView = new System.Windows.Forms.ListView();
            this.columnHeader4 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.columnHeader5 = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.orderBox = new System.Windows.Forms.GroupBox();
            this.productBox = new System.Windows.Forms.GroupBox();
            this.label4 = new System.Windows.Forms.Label();
            this.orderBtn = new System.Windows.Forms.Button();
            this.orderQuantityUpDown = new System.Windows.Forms.NumericUpDown();
            this.productInStockLabel = new System.Windows.Forms.Label();
            this.productDescriptionLabel = new System.Windows.Forms.Label();
            this.productNameLabel = new System.Windows.Forms.Label();
            this.searchBox = new System.Windows.Forms.TextBox();
            this.orderTitleLabel = new System.Windows.Forms.Label();
            this.orderProductLabel = new System.Windows.Forms.Label();
            this.orderShippingState = new System.Windows.Forms.Label();
            this.orderBox.SuspendLayout();
            this.productBox.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.orderQuantityUpDown)).BeginInit();
            this.SuspendLayout();
            // 
            // usernameLabel
            // 
            this.usernameLabel.AutoSize = true;
            this.usernameLabel.Font = new System.Drawing.Font("Comic Sans MS", 15.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.usernameLabel.Location = new System.Drawing.Point(12, 5);
            this.usernameLabel.Name = "usernameLabel";
            this.usernameLabel.Size = new System.Drawing.Size(107, 29);
            this.usernameLabel.TabIndex = 0;
            this.usernameLabel.Text = "username";
            // 
            // ordersListView
            // 
            this.ordersListView.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader3,
            this.columnHeader2});
            this.ordersListView.GridLines = true;
            this.ordersListView.Location = new System.Drawing.Point(12, 72);
            this.ordersListView.Name = "ordersListView";
            this.ordersListView.Size = new System.Drawing.Size(195, 458);
            this.ordersListView.TabIndex = 1;
            this.ordersListView.UseCompatibleStateImageBehavior = false;
            this.ordersListView.View = System.Windows.Forms.View.Details;
            this.ordersListView.ItemActivate += new System.EventHandler(this.ordersListView_ItemActivate);
            // 
            // columnHeader3
            // 
            this.columnHeader3.Text = "Product";
            this.columnHeader3.Width = 150;
            // 
            // columnHeader2
            // 
            this.columnHeader2.Text = "Qty";
            // 
            // productsListView
            // 
            this.productsListView.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader4,
            this.columnHeader5});
            this.productsListView.GridLines = true;
            this.productsListView.Location = new System.Drawing.Point(237, 92);
            this.productsListView.Name = "productsListView";
            this.productsListView.Size = new System.Drawing.Size(220, 438);
            this.productsListView.TabIndex = 2;
            this.productsListView.UseCompatibleStateImageBehavior = false;
            this.productsListView.View = System.Windows.Forms.View.Details;
            this.productsListView.ItemActivate += new System.EventHandler(this.productsListView_ItemActivate);
            // 
            // columnHeader4
            // 
            this.columnHeader4.Text = "ID";
            this.columnHeader4.Width = 30;
            // 
            // columnHeader5
            // 
            this.columnHeader5.Text = "Name";
            this.columnHeader5.Width = 186;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Comic Sans MS", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label1.Location = new System.Drawing.Point(12, 40);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(129, 29);
            this.label1.TabIndex = 3;
            this.label1.Text = "Your orders";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Comic Sans MS", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label2.Location = new System.Drawing.Point(232, 40);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(100, 29);
            this.label2.TabIndex = 4;
            this.label2.Text = "Products";
            // 
            // orderBox
            // 
            this.orderBox.Controls.Add(this.orderShippingState);
            this.orderBox.Controls.Add(this.orderProductLabel);
            this.orderBox.Controls.Add(this.orderTitleLabel);
            this.orderBox.Location = new System.Drawing.Point(480, 72);
            this.orderBox.Name = "orderBox";
            this.orderBox.Size = new System.Drawing.Size(625, 458);
            this.orderBox.TabIndex = 6;
            this.orderBox.TabStop = false;
            this.orderBox.Text = "Order";
            this.orderBox.Visible = false;
            // 
            // productBox
            // 
            this.productBox.Controls.Add(this.label4);
            this.productBox.Controls.Add(this.orderBtn);
            this.productBox.Controls.Add(this.orderQuantityUpDown);
            this.productBox.Controls.Add(this.productInStockLabel);
            this.productBox.Controls.Add(this.productDescriptionLabel);
            this.productBox.Controls.Add(this.productNameLabel);
            this.productBox.Location = new System.Drawing.Point(480, 72);
            this.productBox.Name = "productBox";
            this.productBox.Size = new System.Drawing.Size(625, 458);
            this.productBox.TabIndex = 0;
            this.productBox.TabStop = false;
            this.productBox.Text = "Product";
            this.productBox.Visible = false;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Comic Sans MS", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label4.Location = new System.Drawing.Point(10, 298);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(96, 38);
            this.label4.TabIndex = 6;
            this.label4.Text = "Order";
            // 
            // orderBtn
            // 
            this.orderBtn.Location = new System.Drawing.Point(17, 386);
            this.orderBtn.Name = "orderBtn";
            this.orderBtn.Size = new System.Drawing.Size(75, 23);
            this.orderBtn.TabIndex = 4;
            this.orderBtn.Text = "Order";
            this.orderBtn.UseVisualStyleBackColor = true;
            this.orderBtn.Click += new System.EventHandler(this.orderBtn_Click);
            // 
            // orderQuantityUpDown
            // 
            this.orderQuantityUpDown.Location = new System.Drawing.Point(17, 351);
            this.orderQuantityUpDown.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.orderQuantityUpDown.Name = "orderQuantityUpDown";
            this.orderQuantityUpDown.Size = new System.Drawing.Size(50, 20);
            this.orderQuantityUpDown.TabIndex = 3;
            this.orderQuantityUpDown.Value = new decimal(new int[] {
            1,
            0,
            0,
            0});
            // 
            // productInStockLabel
            // 
            this.productInStockLabel.AutoSize = true;
            this.productInStockLabel.Font = new System.Drawing.Font("Comic Sans MS", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.productInStockLabel.Location = new System.Drawing.Point(11, 165);
            this.productInStockLabel.Name = "productInStockLabel";
            this.productInStockLabel.Size = new System.Drawing.Size(192, 33);
            this.productInStockLabel.TabIndex = 2;
            this.productInStockLabel.Text = "productInStock";
            // 
            // productDescriptionLabel
            // 
            this.productDescriptionLabel.AutoSize = true;
            this.productDescriptionLabel.Font = new System.Drawing.Font("Comic Sans MS", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.productDescriptionLabel.Location = new System.Drawing.Point(10, 75);
            this.productDescriptionLabel.Name = "productDescriptionLabel";
            this.productDescriptionLabel.Size = new System.Drawing.Size(152, 23);
            this.productDescriptionLabel.TabIndex = 1;
            this.productDescriptionLabel.Text = "productDescription";
            // 
            // productNameLabel
            // 
            this.productNameLabel.AutoSize = true;
            this.productNameLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 26.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.productNameLabel.Location = new System.Drawing.Point(7, 20);
            this.productNameLabel.Name = "productNameLabel";
            this.productNameLabel.Size = new System.Drawing.Size(237, 39);
            this.productNameLabel.TabIndex = 0;
            this.productNameLabel.Text = "productName";
            // 
            // searchBox
            // 
            this.searchBox.Location = new System.Drawing.Point(237, 72);
            this.searchBox.Name = "searchBox";
            this.searchBox.Size = new System.Drawing.Size(220, 20);
            this.searchBox.TabIndex = 7;
            this.searchBox.TextChanged += new System.EventHandler(this.searchBox_TextChanged);
            // 
            // orderTitleLabel
            // 
            this.orderTitleLabel.AutoSize = true;
            this.orderTitleLabel.Font = new System.Drawing.Font("Comic Sans MS", 21.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.orderTitleLabel.Location = new System.Drawing.Point(17, 20);
            this.orderTitleLabel.Name = "orderTitleLabel";
            this.orderTitleLabel.Size = new System.Drawing.Size(101, 41);
            this.orderTitleLabel.TabIndex = 0;
            this.orderTitleLabel.Text = "label3";
            // 
            // orderProductLabel
            // 
            this.orderProductLabel.AutoSize = true;
            this.orderProductLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.orderProductLabel.Location = new System.Drawing.Point(21, 98);
            this.orderProductLabel.Name = "orderProductLabel";
            this.orderProductLabel.Size = new System.Drawing.Size(79, 29);
            this.orderProductLabel.TabIndex = 1;
            this.orderProductLabel.Text = "label3";
            // 
            // orderShippingState
            // 
            this.orderShippingState.AutoSize = true;
            this.orderShippingState.Font = new System.Drawing.Font("Comic Sans MS", 20.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.orderShippingState.Location = new System.Drawing.Point(26, 184);
            this.orderShippingState.Name = "orderShippingState";
            this.orderShippingState.Size = new System.Drawing.Size(93, 38);
            this.orderShippingState.TabIndex = 2;
            this.orderShippingState.Text = "label3";
            // 
            // CustomerForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1117, 542);
            this.Controls.Add(this.searchBox);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.productsListView);
            this.Controls.Add(this.ordersListView);
            this.Controls.Add(this.usernameLabel);
            this.Controls.Add(this.orderBox);
            this.Controls.Add(this.productBox);
            this.Name = "CustomerForm";
            this.Text = "CustomerForm";
            this.orderBox.ResumeLayout(false);
            this.orderBox.PerformLayout();
            this.productBox.ResumeLayout(false);
            this.productBox.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.orderQuantityUpDown)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label usernameLabel;
        private System.Windows.Forms.ListView ordersListView;
        private System.Windows.Forms.ListView productsListView;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.GroupBox orderBox;
        private System.Windows.Forms.GroupBox productBox;
        private System.Windows.Forms.Label productDescriptionLabel;
        private System.Windows.Forms.Label productNameLabel;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button orderBtn;
        private System.Windows.Forms.NumericUpDown orderQuantityUpDown;
        private System.Windows.Forms.Label productInStockLabel;
        private System.Windows.Forms.TextBox searchBox;
        private System.Windows.Forms.ColumnHeader columnHeader2;
        private System.Windows.Forms.ColumnHeader columnHeader3;
        private System.Windows.Forms.ColumnHeader columnHeader4;
        private System.Windows.Forms.ColumnHeader columnHeader5;
        private System.Windows.Forms.Label orderShippingState;
        private System.Windows.Forms.Label orderProductLabel;
        private System.Windows.Forms.Label orderTitleLabel;
    }
}