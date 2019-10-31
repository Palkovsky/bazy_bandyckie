namespace Shop.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class AddShippedToOrder : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Orders", "Shipped", c => c.Boolean(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Orders", "Shipped");
        }
    }
}
