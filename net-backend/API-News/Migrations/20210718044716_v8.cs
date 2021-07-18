using Microsoft.EntityFrameworkCore.Migrations;

namespace API_News.Migrations
{
    public partial class v8 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "StatusUserSave",
                table: "UserSaveArticles",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "StatusUserLike",
                table: "UserLikeArticles",
                type: "int",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "StatusUserSave",
                table: "UserSaveArticles");

            migrationBuilder.DropColumn(
                name: "StatusUserLike",
                table: "UserLikeArticles");
        }
    }
}
