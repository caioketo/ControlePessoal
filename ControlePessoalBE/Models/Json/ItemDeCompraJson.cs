using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models.Json
{
    public class ItemDeCompraJson
    {
        public int ItemDeCompraId { get; set; }
        public virtual PrecoJson Preco { get; set; }
        public virtual ProdutoJson Produto { get; set; }

        public ItemDeCompraJson(ItemDeCompraModel model)
        {
            this.ItemDeCompraId = model.ItemDeCompraId;
            this.Preco = new PrecoJson(model.Preco);
            this.Produto = new ProdutoJson(model.Produto);
        }
    }
}