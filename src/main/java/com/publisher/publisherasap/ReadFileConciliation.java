//package com.publisher.publisherasap;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.nio.file.FileSystems;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardWatchEventKinds;
//import java.nio.file.WatchEvent;
//import java.nio.file.WatchKey;
//import java.nio.file.WatchService;
//
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import jakarta.annotation.PostConstruct;
//
//@Component
//public class ReadFileConciliation {
//	@Autowired
//	private QueueSender queueSender;
//
//	private String pathFile = "E:\\desafiohack\\conciliation";
//
//	@PostConstruct
//	public void init() {
//		try {
//			this.listenerFolder(pathFile);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
//
//	public void listenerFolder(String pasta) throws Exception {
//		WatchService watchService = FileSystems.getDefault().newWatchService();
//		Path path = Paths.get(pasta);
//
//		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
//
//		System.out.println("Observando a pasta: " + pasta);
//
//		while (true) {
//			WatchKey key = watchService.take();
//
//			for (WatchEvent<?> event : key.pollEvents()) {
//				if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
//					Path novoArquivo = (Path) event.context();
//					String newFilePath = pasta + "\\" + novoArquivo;
//
//					long fileSizeBefore = Files.size(Paths.get(newFilePath));
//					long fileSizeAfter;
//
//					do {
//						Thread.sleep(1000);
//						fileSizeAfter = Files.size(Paths.get(newFilePath));
//					} while (fileSizeAfter > fileSizeBefore);
//
//					System.out.println("Novo arquivo: " + newFilePath);
//					this.readFile(newFilePath);
//				}
//			}
//
//			key.reset();
//		}
//	}
//
//	private void readFile(String path) {
//		String line;
//
//		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
//
//			while ((line = br.readLine()) != null) {
//				JSONObject obj = this.processLine(line);
//				this.queueSender.sendToConciliation(obj.toString());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private JSONObject processLine(String line) {
//		String[] data = line.split(";");
//		JSONObject obj = new JSONObject();
//		obj.put("transacaoId", data[0]);
//		obj.put("dataTransacao", data[1]);
//		obj.put("documento", data[2]);
//		obj.put("Status", data[3]);
//		return obj;
//	}
//}
