using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models.Json
{
    public class CompraJson
    {
        public int CompraId { get; set; }
        public LocalJson Local { get; set; }
        public virtual List<ItemDeCompraJson> Itens { get; set; }

        public CompraJson(CompraModel model)
        {
            this.Itens = new List<ItemDeCompraJson>();
            this.CompraId = model.CompraId;
            this.Local = new LocalJson(model.Local);
            foreach (ItemDeCompraModel item in model.Itens)
            {
                this.Itens.Add(new ItemDeCompraJson(item));
            }
        }
    }
}