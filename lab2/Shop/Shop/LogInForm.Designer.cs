namespace Shop
{
    partial class LogInForm
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
            this.logInBtn = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.passwordInputBox = new System.Windows.Forms.TextBox();
            this.usernameInputBox = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // logInBtn
            // 
            this.logInBtn.Location = new System.Drawing.Point(107, 193);
            this.logInBtn.Name = "logInBtn";
            this.logInBtn.Size = new System.Drawing.Size(75, 23);
            this.logInBtn.TabIndex = 3;
            this.logInBtn.Text = "Log In";
            this.logInBtn.UseVisualStyleBackColor = true;
            this.logInBtn.Click += new System.EventHandler(this.logInBtn_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(91, 139);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(53, 13);
            this.label2.TabIndex = 1;
            this.label2.Text = "Password";
            // 
            // passwordInputBox
            // 
            this.passwordInputBox.Location = new System.Drawing.Point(94, 155);
            this.passwordInputBox.Name = "passwordInputBox";
            this.passwordInputBox.PasswordChar = '*';
            this.passwordInputBox.Size = new System.Drawing.Size(100, 20);
            this.passwordInputBox.TabIndex = 2;
            // 
            // usernameInputBox
            // 
            this.usernameInputBox.Location = new System.Drawing.Point(94, 104);
            this.usernameInputBox.Name = "usernameInputBox";
            this.usernameInputBox.Size = new System.Drawing.Size(100, 20);
            this.usernameInputBox.TabIndex = 1;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(91, 88);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(55, 13);
            this.label3.TabIndex = 5;
            this.label3.Text = "Username";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Comic Sans MS", 20.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.label4.ForeColor = System.Drawing.Color.Fuchsia;
            this.label4.Location = new System.Drawing.Point(55, 34);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(189, 38);
            this.label4.TabIndex = 6;
            this.label4.Text = "Generic Shop";
            // 
            // LogInForm
            // 
            this.ClientSize = new System.Drawing.Size(284, 261);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.usernameInputBox);
            this.Controls.Add(this.passwordInputBox);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.logInBtn);
            this.Name = "LogInForm";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button logInBtn;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox passwordInputBox;
        private System.Windows.Forms.TextBox usernameInputBox;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
    }
}

