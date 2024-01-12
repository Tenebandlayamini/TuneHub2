package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.PlayList;
import com.example.demo.entities.Song;
import com.example.demo.services.PlayListService;
import com.example.demo.services.SongService;

@Controller
public class PlayListController {
	@Autowired
	SongService songService;
	@Autowired
	PlayListService playlistService;
	
	@GetMapping("/createPlayList")
	public String createPlayList(Model model) {
		List<Song> songList=songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createPlayList";
		
	}
	@PostMapping("/addPlayList")
	public String addPlayList(@ModelAttribute PlayList playlist) {
		playlistService.addPlayList(playlist);
		List<Song> songList=playlist.getSongs();
		for (Song s : songList) {
			s.getPlaylists().add(playlist);
			songService.updateSong(s);
		}
		return "adminHome";
	}
	@GetMapping("/viewPlayLists")
	public String viewPlayList(Model model) {
		List<PlayList> allPlaylists=playlistService.fetchAllPlayLists();
		model.addAttribute("allPlaylists", allPlaylists);
		
		return "displayPlayLists";
	}

}
