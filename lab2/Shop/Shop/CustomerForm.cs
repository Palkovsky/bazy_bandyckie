using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Shop
{
    public partial class CustomerForm : Form
    {
        private ShopContext ctx;
        private Customer user;

        private List<Product> loadedProducts;
        private List<Order> loadedOrders;
        private Product currentProduct;
        private Order currentOrder;

        public CustomerForm(Customer user)
        {
            this.ctx = new ShopContext();
            this.user = user;

            InitializeComponent();
            RefreshAll();
        }

        private void SetProduct(Product product) {
            currentProduct = product;
            currentOrder = null;
            orderBox.Visible = false;

            productNameLabel.Text = product.Name;
            productDescriptionLabel.Text = "Category: " + product.Category.Name;
            productInStockLabel.Text = "Units in stock: " + product.UnitsInStock;
            productBox.Visible = true;
            productBox.BringToFront();
        }

        private void SetOrder(Order order)
        {
            currentProduct = null;
            currentOrder = order;
            orderBox.Visible = true;
            productBox.Visible = false;

            orderTitleLabel.Text = "Order identificator: " + order.OrderId;
            orderProductLabel.Text = order.Quantity + "x " + order.Product.Name;
            if (order.Shipped) {
                orderShippingState.Text = "Order was shipped.";
            } else {
                orderShippingState.Text = "Order awaiting shipment.";
            }

            orderBox.BringToFront();
        }

        private void RefreshProducts() {
            productsListView.Items.Clear();
            string query = searchBox.Text.Trim().ToLower();
            
            if (query.Length > 0)
            {
                loadedProducts = ctx.Products
                    .Include("Category")
                    .Where(p => p.Name.ToLower().Contains(query.ToLower()))
                    .ToList();
            }
            else {
                loadedProducts = ctx.Products
                    .Include("Category")
                    .ToList();
            }

            foreach (Product product in loadedProducts)
            {
                ListViewItem row = new ListViewItem(new String[]{product.ProductId+"", product.Name});
                productsListView.Items.Add(row);
            }
        }

        private void RefreshOrders() {
            ordersListView.Items.Clear();
            loadedOrders = ctx.Orders
                .Include("Product")
                .Where(ord => ord.CustomerId == user.CustomerId)
                .ToList();
            foreach (Order order in loadedOrders)
            {
                ListViewItem row = new ListViewItem(new String[] { order.Product.Name, order.Quantity + "" });
                ordersListView.Items.Add(row);
            }
        }

        private void RefreshAll()
        {
            usernameLabel.Text = "Logged in as " + this.user.CompanyName;
            RefreshProducts();
            RefreshOrders();
        }

        private void orderBtn_Click(object sender, EventArgs e)
        {
            if (currentProduct != null) {
                int qty;
                try
                {
                    qty = Decimal.ToInt32(orderQuantityUpDown.Value);
                }
                catch (OverflowException exception)
                {
                    MessageBox.Show(exception.ToString());
                    return;
                }

                if (qty > currentProduct.UnitsInStock)
                {
                    MessageBox.Show("Invalid quantity. Maximum allowed is " + currentProduct.UnitsInStock + ".");
                    return;
                }

                Order newOrder = new Order { 
                    Quantity = qty, 
                    Shipped = false,
                    CustomerId = user.CustomerId, 
                    ProductId = currentProduct.ProductId 
                };
                currentProduct.UnitsInStock -= qty;
                ctx.Orders.Add(newOrder);
                ctx.SaveChanges();
                RefreshOrders();
                SetProduct(currentProduct);
            }
        }

        private void logOutBtn_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void searchBox_TextChanged(object sender, EventArgs e)
        {
            RefreshProducts();
        }

        private void productsListView_ItemActivate(object sender, EventArgs e)
        {
            int i = productsListView.SelectedIndices[0];
            if (i < loadedProducts.Count) {
                SetProduct(loadedProducts[i]);
            }
        }

        private void ordersListView_ItemActivate(object sender, EventArgs e)
        {
            int i = ordersListView.SelectedIndices[0];
            if (i < loadedOrders.Count)
            {
                SetOrder(loadedOrders[i]);
            }
        }
    }
}
