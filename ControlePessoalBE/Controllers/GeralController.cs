using ControlePessoalBE.Models;
using ControlePessoalBE.Models.Json;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace ControlePessoalBE.Controllers
{
    public class GeralController : Controller
    {
        private CPContext db = new CPContext();

        #region Produtos
        //PRODUTOS
        public JsonResult Produtos()
        {
            List<ProdutoJson> prods = new List<ProdutoJson>();
            foreach (ProdutoModel produto in db.Produtos.ToList())
            {
                prods.Add(new ProdutoJson(produto));
            }
            return Json(prods, JsonRequestBehavior.AllowGet);
        }

        public JsonResult AddCodigo(int produtoId, string codigo)
        {
            ProdutoModel produto = db.Produtos.Where(p => p.ProdutoId == produtoId).First();
            if (produto != null)
            {
                CodigoModel codigoM = new CodigoModel();
                codigoM.Cadastro = DateTime.Now;
                codigoM.ProdutoID = produtoId;
                codigoM.Codigo = codigo;
                codigoM = db.Codigos.Add(codigoM);
                db.SaveChanges();
                produto.Codigos.Add(codigoM);
                db.Entry(produto).State = EntityState.Modified;
                db.SaveChanges();
            }
            return Json(produto, JsonRequestBehavior.AllowGet);
        }

        public JsonResult FindCodigo(string codigo)
        {
            return Json(new ProdutoJson(db.Produtos.Where(p => p.ProdutoId == db.Codigos.Where(c => c.Codigo.Equals(codigo)).FirstOrDefault().ProdutoID).First()), JsonRequestBehavior.AllowGet);
        }

        public JsonResult Codigos(int produtoId)
        {
            List<CodigoJson> codigos = new List<CodigoJson>();
            foreach (CodigoModel codigo in db.Codigos.Where(c => c.ProdutoID == produtoId).ToList())
            {
                codigos.Add(new CodigoJson(codigo));
            }
            return Json(codigos, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult CreateProdSTR(string descricao, double quantidade, double quantidadeaviso)
        {
            ProdutoModel produto = new ProdutoModel();
            produto.Descricao = descricao;
            produto.Quantidade = quantidade;
            produto.QuantidadeAviso = quantidadeaviso;
            produto = db.Produtos.Add(produto);
            db.SaveChanges();
            return Json(produto, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult CreateProd(ProdutoModel produto)
        {
            produto = db.Produtos.Add(produto);
            db.SaveChanges();
            return Json(produto, JsonRequestBehavior.AllowGet);
        }

        //
        #endregion

        #region Alertas
        public JsonResult Alertas()
        {
            List<ProdutoJson> prods = new List<ProdutoJson>();
            foreach (ProdutoModel produto in db.Produtos.Where(p => p.Quantidade <= p.QuantidadeAviso).ToList())
            {
                prods.Add(new ProdutoJson(produto));
            }
            return Json(prods, JsonRequestBehavior.AllowGet);
        }
        #endregion

        #region Compras
        //COMPRAS
        public JsonResult Compras()
        {
            List<CompraJson> compras = new List<CompraJson>();
            foreach (CompraModel compra in db.Compras.ToList())
            {
                compras.Add(new CompraJson(compra));
            }
            return Json(compras, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult CreateCompra(CompraModel compramodel)
        {
            compramodel = db.Compras.Add(compramodel);
            db.SaveChanges();
            return Json(new CompraJson(compramodel), JsonRequestBehavior.AllowGet);
        }


        #endregion

        #region Locais
        //LOCAIS
        public JsonResult Locais()
        {
            return Json(db.Locais.ToList(), JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult CreateLocal(LocalModel local)
        {
            local = db.Locais.Add(local);
            db.SaveChanges();
            return Json(local, JsonRequestBehavior.AllowGet);
        }
        
        #endregion

        #region Receitas
        //RECEITAS
        public JsonResult Receitas()
        {
            List<ReceitaJson> receitas = new List<ReceitaJson>();
            foreach (ReceitaModel receita in db.Receitas.ToList())
            {
                receitas.Add(new ReceitaJson(receita));
            }
            return Json(receitas, JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult CreateReceita(ReceitaModel receita)
        {
            receita = db.Receitas.Add(receita);
            db.SaveChanges();
            return Json(new ReceitaJson(receita), JsonRequestBehavior.AllowGet);
        }
        #endregion

        #region Listas
        public JsonResult ListaAberta()
        {
            ListaModel listaAberta = db.Listas.Where(l => l.Aberta == true).FirstOrDefault();
            return Json(new ListaJson(listaAberta), JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult AddProdutoLista(ProdutoModel produto)
        {
            ListaModel listaAberta = db.Listas.Where(l => l.Aberta == true).FirstOrDefault();
            if (listaAberta == null)
            {
                listaAberta = new ListaModel();
                listaAberta.Aberta = true;
                listaAberta = db.Listas.Add(listaAberta);
            }
            ItemDeListaModel item = new ItemDeListaModel();
            item.ListaID = listaAberta.ListaId;
            item.ProdutoID = produto.ProdutoId;
            listaAberta.Itens.Add(item);
            db.Entry(listaAberta).State = EntityState.Modified;
            db.SaveChanges();
            return Json(new ListaJson(listaAberta), JsonRequestBehavior.AllowGet);
        }
        #endregion

        #region Testes
        public JsonResult Teste()
        {
            TesteModel teste = new TesteModel();
            teste.Descricao = "TaskerOK";
            teste = db.Testes.Add(teste);
            db.SaveChanges();
            return Json(teste, JsonRequestBehavior.AllowGet);
        }

        public JsonResult Testes()
        {
            return Json(db.Testes.ToList(), JsonRequestBehavior.AllowGet);
        }
        #endregion
    }
}
