using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace Shop {
    public class Order
    {
        public int OrderId { get; set; }
        public int Quantity { get; set; }
        public bool Shipped { get; set; }

        [ForeignKey("Customer")]
        public int CustomerId { get; set; }
        public Customer Customer { get; set; }

        [ForeignKey("Product")]
        public int ProductId { get; set; }
        public Product Product { get; set; }
    }
}
