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
        }

        public DbSet<ProdutoModel> Produtos { get; set; }
        public DbSet<PrecoModel> Precos { get; set; }
        public DbSet<CodigoModel> Codigos { get; set; }
    }
}