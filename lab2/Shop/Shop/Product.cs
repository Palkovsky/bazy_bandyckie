using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Shop
{
    public class Product
    {
        public Product() {
            Orders = new List<Order>();
        }

        public int ProductId { get; set; }
        public string Name { get; set; }
        public int UnitsInStock { get; set; }

        [ForeignKey("Category")]
        public int CategoryId { get; set; }
        public Category Category { get; set; }

        public List<Order> Orders { get; set; }
    }
}
