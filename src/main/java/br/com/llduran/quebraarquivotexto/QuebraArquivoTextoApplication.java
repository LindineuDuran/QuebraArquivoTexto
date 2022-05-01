package br.com.llduran.quebraarquivotexto;

import br.com.llduran.quebraarquivotexto.exception.NegocioException;
import br.com.llduran.quebraarquivotexto.util.IoUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class QuebraArquivoTextoApplication implements CommandLineRunner
{
	@Autowired
	private IoUtils ioUtils;

	public static void main(String[] args)
	{
		SpringApplication.run(QuebraArquivoTextoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		String nomeArquivo = "";
		String jsonKey = "";
		String prefixArquivo = "";

		nomeArquivo = "CompraFinalizadaFinal.json";
		jsonKey = "compra_finalizada";
		prefixArquivo = "compra";
		quebraArrayJson(nomeArquivo, jsonKey, prefixArquivo);

		nomeArquivo = "Filme.json";
		jsonKey = "filme";
		prefixArquivo = "filme";
		quebraArrayJson(nomeArquivo, jsonKey, prefixArquivo);

		nomeArquivo = "Pessoa.json";
		jsonKey = "pessoa";
		prefixArquivo = "pessoa";
		quebraArrayJson(nomeArquivo, jsonKey, prefixArquivo);

		nomeArquivo = "Pedido.json";
		jsonKey = "pedido";
		prefixArquivo = "pedido";
		quebraArrayJson(nomeArquivo, jsonKey, prefixArquivo);
	}

	private void quebraArrayJson(String nomeArquivo, String jsonKey, String prefixArquivo) throws IOException
	{
		// Ler arquivo Json
		String jsoncontent = ioUtils.readFile(nomeArquivo);

		JSONObject obj = null;
		try
		{
			obj = new JSONObject(jsoncontent);
			JSONArray jArray = obj.getJSONArray(jsonKey);
			for (int i = 0; i < jArray.length(); i++)
			{
				JSONObject o = jArray.getJSONObject(i);
				System.out.println(o.toString());
				String contador = String.format("%03d", i);
				ioUtils.writeStream(prefixArquivo + contador + ".json", o.toString());
			}
		}
		catch (JSONException e)
		{
			throw new NegocioException("Erro ao interpretar json", e);
		}
	}
}
