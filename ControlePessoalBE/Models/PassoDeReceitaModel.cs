using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models
{
    public class PassoDeReceitaModel
    {
        [Key]
        public int PassoDeReceitaId { get; set; }

        [ForeignKey("Receita")]
        [Column(Order = 2)]
        public int? ReceitaID { get; set; }
        public virtual ReceitaModel Receita { get; set; }

        public int Ordem { get; set; }
        public string Descricao { get; set; }
    }
}