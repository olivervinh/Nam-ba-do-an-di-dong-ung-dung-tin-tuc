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
    public class UserSaveArticlesController : ControllerBase
    {

        private readonly DPContext _context;
        public UserSaveArticlesController(DPContext context)
        {
            this._context = context;
        }
        [HttpPost]
        public async Task<ActionResult<UserSaveArticles>> Postasync([FromForm] string idUser, [FromForm] string idArticle, [FromForm] string status)
        {
            int article = int.Parse(idArticle);
            int user = int.Parse(idUser);
            int statuslike = int.Parse(status);
            UserSaveArticles userSaveArticles = new UserSaveArticles()
            {
                IdArticle = article,
                IdUser = user,
                StatusUserSave = statuslike,
            };
            _context.UserSaveArticles.Add(userSaveArticles);
            await _context.SaveChangesAsync();
            return Ok();
        }
        [HttpDelete]
        public async Task<ActionResult> DeleteAsync([FromForm] int idUser, [FromForm] int idArticle)
        {
            UserSaveArticles save = _context.UserSaveArticles.FirstOrDefault(s => s.IdArticle == idArticle && s.IdUser == idUser);
            _context.UserSaveArticles.Remove(save);
            await _context.SaveChangesAsync();
            return Ok();
        }
    }
}
