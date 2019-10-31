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
    public partial class AdminForm : Form
    {
        private ShopContext ctx;
        private Customer user;

        public AdminForm(Customer user)
        {
            this.ctx = new ShopContext();
            this.user = user;

            InitializeComponent();
            BindAll();
        }

        private void BindAll() {
            ctx.Categories.Load();
            categoryDataGridView.DataSource = ctx.Categories.Local.ToBindingList();

            ctx.Customers.Load();
            customerDataGridView.DataSource = ctx.Customers.Local.ToBindingList();

            ctx.Orders.Include("Customer").Include("Product").Load();
            orderDataGridView.DataSource = ctx.Orders.Local.ToBindingList();

            ctx.Products.Include("Category").Load();
            productDataGridView.DataSource = ctx.Products.Local.ToBindingList();
        }

        private void categoryDataGridView_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void categorySaveBtn_Click(object sender, EventArgs e)
        {
            this.ctx.SaveChanges();
            categoryDataGridView.Refresh();
        }

        private void customersSaveBtn_Click(object sender, EventArgs e)
        {
            this.ctx.SaveChanges();
            customerDataGridView.Refresh();
        }

        private void orderSaveBtn_Click(object sender, EventArgs e)
        {
            this.ctx.SaveChanges();
            orderDataGridView.Refresh();
        }

        private void productBtn_Click(object sender, EventArgs e)
        {
            this.ctx.SaveChanges();
            productDataGridView.Refresh();
        }
    }
}
