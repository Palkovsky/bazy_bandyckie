using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Shop {
    public class Customer
    {
        public Customer() {
            Orders = new List<Order>();
        }

        public int CustomerId {get; set; }
        public string CompanyName { get; set; }
        public string Password { get; set; }
        public string Description { get; set; }
        public bool Admin { get; set; }
        public List<Order> Orders { get; set; }
    }
}