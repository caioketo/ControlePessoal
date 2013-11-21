using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models
{
    public class ItemDeCompraModel
    {
        [Key]
        public int ItemDeCompraId { get; set; }

        [ForeignKey("Compra")]
        [Column(Order = 4)]
        public int? CompraID { get; set; }
        public virtual CompraModel Compra { get; set; }

        public virtual List<PrecoModel> Precos { get; set; }
        public virtual List<ProdutoModel> Produtos { get; set; }

        public ItemDeCompraModel()
        {
            Precos = new List<PrecoModel>();
            Produtos = new List<ProdutoModel>();
        }
    }
}