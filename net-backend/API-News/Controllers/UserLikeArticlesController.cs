using API_News.Data;
using API_News.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API_News.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserLikeArticlesController : ControllerBase
    {
        private readonly DPContext _context; 
        public UserLikeArticlesController(DPContext context)
        {
            this._context = context;
        }
        [HttpPost]
        public async Task<ActionResult<UserLikeArticles>> Postasync([FromForm] string idUser, [FromForm] string idArticle, [FromForm] string status)
        {
            int article = int.Parse(idArticle);
            int user = int.Parse(idUser);
            int statuslike = int.Parse(status);
            UserLikeArticles userLikeArticles = new UserLikeArticles()
            {
                IdArticle = article,
                IdUser = user,
                StatusUserLike = statuslike,
            };
            _context.UserLikeArticles.Add(userLikeArticles);
            await _context.SaveChangesAsync();
            return Ok();
        }
        [HttpDelete]
        public async Task<ActionResult> DeleteAsync([FromForm] int idUser, [FromForm] int idArticle)
        {
            UserLikeArticles like = _context.UserLikeArticles.FirstOrDefault(s => s.IdArticle == idArticle && s.IdUser == idUser);
            _context.UserLikeArticles.Remove(like);
            await _context.SaveChangesAsync();
            return Ok();
        }
    }
}
