using System;
using System.Collections.Generic;

namespace Shop
{
    public class Category
    {
        public Category() {
            Products = new List<Product>();
        }

        public int CategoryId { get; set; }
        public string Name { get; set; }
        public List<Product> Products { get; set; }
    }
}