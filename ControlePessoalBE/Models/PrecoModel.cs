using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models
{
    public class PrecoModel
    {
        [Key]
        public int PrecoId { get; set; }
        public DateTime Cadastro { get; set; }
        [ForeignKey("Produto")]
        [Column(Order = 2)]
        public int? ProdutoID { get; set; }
        public virtual ProdutoModel Produto { get; set; }

        [ForeignKey("ItemDeCompra")]
        [Column(Order = 3)]
        public int? ItemDeCompraID { get; set; }
        public virtual ItemDeCompraModel ItemDeCompra { get; set; }

        public double Preco { get; set; }
    }
}