package br.com.gft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.gft.controller.repository.filter.TituloFilter;
import br.com.gft.model.StatusTitulo;
import br.com.gft.model.Titulo;
import br.com.gft.repository.Titulos;

@Service
public class CadastroTituloService 
{
	@Autowired
	private Titulos titulos;
	
	public void salvar (Titulo titulo)
	{
		try
		{
			titulos.save(titulo);
		}
		catch(DataIntegrityViolationException e)
		{
			throw new IllegalArgumentException("Formato de data inválido");
			
		}
	}
	
	public void excluir(Long codigo)
	{
		titulos.deleteById(codigo);
	}
	
	
	public String receber(Long codigo)
	{
		Titulo titulo =  titulos.getOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter filtro)
	{
		String descricao = filtro.getDescricao() == null ? "" : filtro.getDescricao();
		return titulos.findByDescricaoContaining(descricao);
	}
	

}
