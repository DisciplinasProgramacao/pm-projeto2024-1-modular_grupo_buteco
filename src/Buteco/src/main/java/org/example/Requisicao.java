package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** 
 * MIT License
 *
 * Copyright(c) 2024 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

 /**
  * Classe requisicao: representa o atendimento de um cliente, em uma mesa, em uma data
  */
public class Requisicao {

	private Cliente cliente;
	private Mesa mesa;
	private int quantPessoas;
	private LocalDateTime entrada;
	private LocalDateTime saida;
	private boolean encerrada;

	/**
	 * Cria uma requisição para quantPessoas em nome do cliente. A requisição é criada sem horário de entrada nem saída, sem mesa e com estado de não encerrada
	 * @param quantPessoas Quantidade de pessoas a serem atendidas na mesa. Deve ser >= 1
	 * @param cliente Cliente que faz o pedido. Não deve ser nulo.
	 */
	public Requisicao(int quantPessoas, Cliente cliente) {
		this.quantPessoas = 1;
		if(quantPessoas > 1 )
			this.quantPessoas = quantPessoas;
		this.cliente = cliente;
		entrada = saida = null;
		mesa = null;
		encerrada = false;
	}

	/**
	 * Encerra uma requisição, caso não tenha sido encerrada anteriormente, marcando o horário de finalização e desocupa sua mesa. Retorna a mesa desocupada
	 * @return A mesa desocupada, ou null caso a requisição não estivesse ativa.
	 */
	public Mesa encerrar() {
		Mesa liberada = null;
		if(!encerrada && mesa!=null ){
			saida = LocalDateTime.now();
			mesa.desocupar();
			liberada = mesa;
			encerrada = true;
		}
		return liberada;
	}

	/**
	 * Aloca uma mesa para a requisição, desde que esta mesa esteja disponível e a requisição não tenha sido encerrada. Ajusta a hora de atendimento, se a mesa for alocada.
	 * @param mesa Mesa a ser alocada para a requisição.
	 */
	public void alocarMesa(Mesa mesa) {
		if(!encerrada && mesa.estahLiberada(quantPessoas)){
			this.mesa = mesa;
			entrada = LocalDateTime.now();
			this.mesa.ocupar();
		}
	}

	/**
	 * Responde se a requisição já foi encerrada
	 * @return TRUE/FALSE conforme a requisição já foi encerrada ou não.
	 */
	public boolean estahEncerrada(){
		return encerrada;
	}

	/**
	 * Responde se esta requisição pertence à mesa em questão, passada por parâmetro
	 * @param idMesa Número da mesa
	 * @return TRUE/FALSE conforme a requisição pertença ou não àquela mesa.
	 */
	public boolean ehDaMesa(int idMesa){
		return idMesa == mesa.getIdMesa();
	}

	
	public int getQuantPessoas(){
		return quantPessoas;
	}

	public String toString(){
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		StringBuilder stringReq = new StringBuilder(cliente.toString());
		if(mesa!=null){
			stringReq.append("\n"+mesa.toString()+"\n");
			stringReq.append("Entrada em "+ formato.format(entrada)+"\n");
			if(saida!=null)
				stringReq.append("Saída em "+formato.format(saida)+"\n");
		}
		else{
			stringReq.append(" Em espera.");
		}
		return stringReq.toString();
	}
}
