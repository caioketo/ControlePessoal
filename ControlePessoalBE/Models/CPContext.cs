using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models
{
    public class CPContext : DbContext
    {
        public CPContext()
            : base("DefaultConnection")
        {
            Database.SetInitializer<CPContext>(new DropCreateDatabaseIfModelChanges<CPContext>());
        }

        public DbSet<ProdutoModel> Produtos { get; set; }
        public DbSet<PrecoModel> Precos { get; set; }
        public DbSet<CodigoModel> Codigos { get; set; }
        public DbSet<CompraModel> Compras { get; set; }
        public DbSet<ItemDeCompraModel> ItensDeCompra { get; set; }
        public DbSet<LocalModel> Locais { get; set; }
        public DbSet<ReceitaModel> Receitas { get; set; }
    }
}