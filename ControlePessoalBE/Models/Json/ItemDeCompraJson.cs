using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models.Json
{
    public class ItemDeCompraJson
    {
        public int ItemDeCompraId { get; set; }
        public virtual List<PrecoJson> Precos { get; set; }
        public virtual List<ProdutoJson> Produtos { get; set; }

        public ItemDeCompraJson(ItemDeCompraModel model)
        {
            Precos = new List<PrecoJson>();
            Produtos = new List<ProdutoJson>();
            this.ItemDeCompraId = model.ItemDeCompraId;
            foreach (PrecoModel preco in model.Precos)
            {
                this.Precos.Add(new PrecoJson(preco));
            }

            foreach (ProdutoModel produto in model.Produtos)
            {
                this.Produtos.Add(new ProdutoJson(produto));
            }
        }
    }
}