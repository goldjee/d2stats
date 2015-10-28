import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Ins on 28.10.2015.
 */
public class mExport {

    public mExport() {}

    public void exportMatchToHTML(List<cMatch> matchArray) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("<!doctype html>\r\n" +
                "<html>\r\n" +
                "<head>\r\n" +
                "\t<title>Retrieved exported match stats</title>\r\n" +
                "</head>\r\n" +
                "<body>\r\n");

        for (cMatch match : matchArray) {
            sb.append("<h2>Match ID " + match.matchID + "</h2>\r\n");
            if (match.players.size() > 0) {
                sb.append("<table>\r\n" +
                        "\t<tr>\r\n" +
                        "\t\t<th>PlayerID</th>\r\n" +
                        "\t\t<th>PlayerSlot</th>\r\n" +
                        "\t\t<th>HeroID</th>\r\n" +
                        "\t\t<th>K</th>\r\n" +
                        "\t\t<th>D</th>\r\n" +
                        "\t\t<th>A</th>\r\n" +
                        "\t</tr>\r\n");
                for (cPlayer player : match.players) {
                    sb.append("\t<tr>\r\n" +
                            "\t\t<td>" + String.valueOf(player.accountID) + "</td>\r\n" +
                            "\t\t<td>" + String.valueOf(player.statsMatch.playerSlot) + "</td>\r\n" +
                            "\t\t<td>" + String.valueOf(player.statsMatch.heroID) + "</td>\r\n" +
                            "\t\t<td>" + String.valueOf(player.statsMatch.kills) + "</td>\r\n" +
                            "\t\t<td>" + String.valueOf(player.statsMatch.deaths) + "</td>\r\n" +
                            "\t\t<td>" + String.valueOf(player.statsMatch.assists) + "</td>\r\n" +
                            "\t</th>\r\n");
                }
                sb.append("</table>\r\n");
            }
        }

        sb.append("</body>\r\n" +
                "</html>");

        String fileName = "export.html";
        String fileContents = sb.toString();
        Path file = Paths.get(fileName);
        if (!Files.exists(file)) {
            Files.createFile(file);
        }
        Files.write(file, fileContents.getBytes());
    }
}
