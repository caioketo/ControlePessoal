using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models
{
    public class CompraModel
    {
        [Key]
        public int CompraId { get; set; }
        [ForeignKey("Local")]
        [Column(Order = 4)]
        public int? LocalID { get; set; }
        public virtual LocalModel Local { get; set; }

        public virtual List<ItemDeCompraModel> Itens { get; set; }

        public CompraModel()
        {
            Itens = new List<ItemDeCompraModel>();
        }
    }
}