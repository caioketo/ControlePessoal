using ControlePessoalBE.Models;
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
            return Json(db.Produtos.ToList(), JsonRequestBehavior.AllowGet);
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
            return Json(db.Produtos.Where(p => p.ProdutoId == db.Codigos.Where(c => c.Codigo.Equals(codigo)).FirstOrDefault().ProdutoID).First(), JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult CreateProd(string descricao, double quantidade, double quantidadeaviso)
        {
            ProdutoModel produto = new ProdutoModel();
            produto.Descricao = descricao;
            produto.Quantidade = quantidade;
            produto.QuantidadeAviso = quantidadeaviso;
            produto = db.Produtos.Add(produto);
            db.SaveChanges();
            return Json(produto, JsonRequestBehavior.AllowGet);
        }
        //
        #endregion

        #region Compras
        //COMPRAS
        public JsonResult Compras()
        {
            return Json(db.Compras.ToList(), JsonRequestBehavior.AllowGet);
        }


        #endregion

        #region Locais
        //LOCAIS
        public JsonResult Locais()
        {
            return Json(db.Locais.ToList(), JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult CreateLocal(string descricao)
        {
            LocalModel local = new LocalModel();
            local.Descricao = descricao;
            local = db.Locais.Add(local);
            db.SaveChanges();
            return Json(local, JsonRequestBehavior.AllowGet);
        }


        #endregion
    }
}
