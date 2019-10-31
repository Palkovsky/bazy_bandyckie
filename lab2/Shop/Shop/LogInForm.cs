using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.Entity;

namespace Shop
{
    public partial class LogInForm : Form
    {
        private ShopContext db;

        public LogInForm()
        {
            db = new ShopContext();

            InitializeComponent();
        }

        private void logInBtn_Click(object sender, EventArgs e)
        {
            string username = usernameInputBox.Text;
            string password = passwordInputBox.Text;
            Customer user = (from c in db.Customers
                             where c.CompanyName == username && c.Password == password
                             select c)
                       .DefaultIfEmpty(null)
                       .First();

            if (user == null)
            {
                MessageBox.Show("Invalid credentials.");
            }
            else if (user.Admin)
            {
                this.Hide();
                AdminForm form = new AdminForm(user);
                form.Text = "Admin Panel";
                form.MaximizeBox = false;
                form.MinimizeBox = false;
                form.FormBorderStyle = FormBorderStyle.FixedSingle;
                form.ShowDialog();
                this.Close();
            }
            else 
            {
                this.Hide();
                CustomerForm form = new CustomerForm(user);
                form.Text = "Online Shop";
                form.MaximizeBox = false;
                form.MinimizeBox = false;
                form.FormBorderStyle = FormBorderStyle.FixedSingle;
                form.ShowDialog();
                this.Close();
            }
        }
    }
}
