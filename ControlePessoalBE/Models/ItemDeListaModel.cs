using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models
{
    public class ItemDeListaModel
    {
        [Key]
        public int ItemDeListaId { get; set; }

        [ForeignKey("Lista")]
        [Column(Order = 2)]
        public int? ListaID { get; set; }
        public virtual ListaModel Lista { get; set; }
        [ForeignKey("Produto")]
        [Column(Order = 3)]
        public int? ProdutoID { get; set; }
        public virtual ProdutoModel Produto { get; set; }

        public ItemDeListaModel()
        {
        }
    }
}