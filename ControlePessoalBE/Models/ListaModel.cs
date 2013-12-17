using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models
{
    public class ListaModel
    {
        [Key]
        public int ListaId { get; set; }
        public bool Aberta { get; set; }

        public virtual List<ItemDeListaModel> Itens { get; set; }

        public ListaModel()
        {
            Itens = new List<ItemDeListaModel>();
        }

    }
}