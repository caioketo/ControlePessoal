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
        public int ReceitaModelId { get; set; }
        public virtual List<ItemDeReceitaModel> Igredientes { get; set; }

        public ReceitaModel()
        {
            Igredientes = new List<ItemDeReceitaModel>();
        }
    }
}