using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models
{
    public class ProdutoModel
    {
        [Key]
        public int ProdutoId { get; set; }
        public string Descricao { get; set; }
        public double Quantidade { get; set; }
        public double QuantidadeAviso { get; set; }
        public virtual List<PrecoModel> Precos { get; set; }
        public virtual List<CodigoModel> Codigos { get; set; }

        public ProdutoModel()
        {
            Precos = new List<PrecoModel>();
            Codigos = new List<CodigoModel>();
        }
    }
}