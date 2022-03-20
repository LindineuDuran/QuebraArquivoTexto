package br.com.llduran.quebraarquivotexto.util;

import br.com.llduran.quebraarquivotexto.exception.NegocioException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;

@Data
@Component
public class IoUtils
{
	@Value("${llduran.storage.source-folder}")
	private String sourceFolder;

	@Value("${llduran.storage.target-folder}")
	private String targetFolder;

	public String readFile(String nomeArquivo) throws IOException
	{
		String filePath = sourceFolder + "\\" + nomeArquivo;
		Path path = Paths.get(filePath);

		String listFile = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\r\n"));

		return listFile;
	}

	public void writeStream(String nomeArquivo, String dados) throws IOException
	{
		String filePath = targetFolder + "\\" + nomeArquivo;

		apagaSeExiste(filePath);

		Path path = Paths.get(filePath);
		if (dados.length() > 0)
		{
			try
			{
				Files.write(path, Collections.singleton(dados));
			}
			catch (IOException e)
			{
				throw new NegocioException("Erro ao gravar arquivo!", e);
			}
		}
	}

	private static void apagaSeExiste(String nomeArquivo)
	{
		Path path = Paths.get(nomeArquivo);
		if (Files.exists(path))
		{
			try
			{
				Files.delete(path);
			}
			catch (IOException e)
			{
				throw new NegocioException("Erro ao apagar arquivo!", e);
			}
		}
	}
}
