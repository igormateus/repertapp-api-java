package io.github.igormateus.repertapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.igormateus.repertapp.dto.band.BandCreateDTO;
import io.github.igormateus.repertapp.dto.band.BandResponseDTO;
import io.github.igormateus.repertapp.dto.band.BandResponseSummaryDTO;
import io.github.igormateus.repertapp.dto.band.BandUpdateDTO;
import io.github.igormateus.repertapp.model.Band;
import io.github.igormateus.repertapp.service.BandService;
import io.github.igormateus.repertapp.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bands")
@RequiredArgsConstructor
public class BandController {
    
    private final BandService bandService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<BandResponseDTO> create(@RequestBody BandCreateDTO band, HttpServletRequest req) {
        Band bandSaved = bandService.create(modelMapper.map(band, Band.class), userService.whoami(req));

        return ResponseEntity.ok(modelMapper.map(bandSaved, BandResponseDTO.class));
    }

    @GetMapping
    public ResponseEntity<Page<BandResponseSummaryDTO>> findAllByUserAuth(HttpServletRequest req, Pageable page) {
        Page<Band> bandsPage = bandService.findAllByUser(userService.whoami(req), page);

        return ResponseEntity.ok(bandsPage.map(band -> modelMapper.map(band, BandResponseSummaryDTO.class)));
    }

    @GetMapping("/{bandId}")
    public ResponseEntity<BandResponseDTO> findByIdAndUserAuth(@PathVariable Long bandId, HttpServletRequest req) {
        Band band = bandService.findByIdAndUser(bandId, userService.whoami(req));

        return ResponseEntity.ok(modelMapper.map(band, BandResponseDTO.class));
    }

    @PutMapping("/{bandId}")
    public ResponseEntity<BandResponseDTO> edit(@PathVariable Long bandId, @RequestBody BandUpdateDTO band, HttpServletRequest req){
        Band bandSaved = bandService.edit(bandId, band, userService.whoami(req));

        return ResponseEntity.ok(modelMapper.map(bandSaved, BandResponseDTO.class));
    }

    @DeleteMapping("/{bandId}")
    public ResponseEntity<Void> delete(@PathVariable Long bandId, HttpServletRequest req) {
        bandService.delete(bandId, userService.whoami(req));

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{bandId}/add-member/{memberId}")
    public ResponseEntity<BandResponseDTO> addMember(@PathVariable Long bandId, @PathVariable Long memberId, HttpServletRequest req) {
        Band band = bandService.addMember(bandId, memberId, userService.whoami(req));

        return ResponseEntity.ok(modelMapper.map(band, BandResponseDTO.class));
    }

    @PostMapping("/{bandId}/remove-member/{memberId}")
    public ResponseEntity<BandResponseDTO> removeMember(@PathVariable Long bandId, @PathVariable Long memberId, HttpServletRequest req) {
        Band band = bandService.removeMember(bandId, memberId, userService.whoami(req));

        return ResponseEntity.ok(modelMapper.map(band, BandResponseDTO.class));
    }
}
