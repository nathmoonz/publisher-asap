package com.publisher.publisherasap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ReadFileInput {

	@Autowired
	private QueueSender queueSender;

	private String pathFile = "E:\\desafiohack\\input";

	@PostConstruct
	public void init() {
		try {
			this.listenerFolder(pathFile);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void listenerFolder(String pasta) throws Exception {
		WatchService watchService = FileSystems.getDefault().newWatchService();
		Path path = Paths.get(pasta);

		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

		System.out.println("Observando a pasta: " + pasta);

		while (true) {
			WatchKey key = watchService.take();

			for (WatchEvent<?> event : key.pollEvents()) {
				if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
					Path novoArquivo = (Path) event.context();
					String newFilePath = pasta + "\\" + novoArquivo;

					long fileSizeBefore = Files.size(Paths.get(newFilePath));
					long fileSizeAfter;

					do {
						Thread.sleep(1000);
						fileSizeAfter = Files.size(Paths.get(newFilePath));
					} while (fileSizeAfter > fileSizeBefore);

					System.out.println("Novo arquivo: " + newFilePath);
					this.readFile(newFilePath);
				}
			}

			key.reset();
		}
	}

	private void readFile(String path) {
		String line;

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			while ((line = br.readLine()) != null) {
				JSONObject obj = this.processLine(line);
				this.queueSender.sendToCreate(obj.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JSONObject processLine(String line) {
		String[] data = line.split(";");
		JSONObject obj = new JSONObject();
		obj.put("transacaoId", data[0]);
		obj.put("dataTransacao", data[1]);
		obj.put("documento", data[2]);
		obj.put("nome", data[3]);
		obj.put("idade", data[4]);
		obj.put("valor", data[5]);
		obj.put("numParcelas", data[6]);
		return obj;
	}
}