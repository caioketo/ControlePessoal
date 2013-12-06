using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models
{
    public class ItemDeReceitaModel
    {
        [Key]
        public int ItemDeReceitaModelId { get; set; }

        [ForeignKey("Receita")]
        [Column(Order = 2)]
        public int? ReceitaID { get; set; }
        public virtual ReceitaModel Receita { get; set; }
        [ForeignKey("Produto")]
        [Column(Order = 3)]
        public int? ProdutoID { get; set; }
        public virtual ProdutoModel Produto { get; set; }
        public double QuantidadeUtilizada { get; set; }

        public ItemDeReceitaModel()
        {
        }
    }
}