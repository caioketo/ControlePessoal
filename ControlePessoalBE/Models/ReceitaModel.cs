using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models
{
    public class ReceitaModel
    {
        [Key]
        public int ReceitaId { get; set; }
        public virtual List<ItemDeReceitaModel> Igredientes { get; set; }
        public virtual List<PassoDeReceitaModel> Passos { get; set; }
        public string Descricao { get; set; }

        public ReceitaModel()
        {
            Igredientes = new List<ItemDeReceitaModel>();
            Passos = new List<PassoDeReceitaModel>();
        }
    }
}