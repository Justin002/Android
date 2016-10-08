package com.beyondsoft.ep2p.model.response;

/**
 * @author Ivan.Lu
 * @description 资产统计
 */
public class AssetStatisticsResponse extends BaseResponse {

	private Result result;

	public class Result {
		private Accountinfovo accountinfovo;

		public class Accountinfovo {
			private double availableBalance;
			private double freezingAmount;
			private double dueinAmount;
			private double dueinInterest;
			private double investInterest;
			private double rateInterest;
			private double investmentIncentive;
			private double redEnvelope;
			private double recommendedAwards;
			public double getAvailableBalance() {
				return availableBalance;
			}
			public void setAvailableBalance(double availableBalance) {
				this.availableBalance = availableBalance;
			}
			public double getFreezingAmount() {
				return freezingAmount;
			}
			public void setFreezingAmount(double freezingAmount) {
				this.freezingAmount = freezingAmount;
			}
			public double getDueinAmount() {
				return dueinAmount;
			}
			public void setDueinAmount(double dueinAmount) {
				this.dueinAmount = dueinAmount;
			}
			public double getDueinInterest() {
				return dueinInterest;
			}
			public void setDueinInterest(double dueinInterest) {
				this.dueinInterest = dueinInterest;
			}
			public double getInvestInterest() {
				return investInterest;
			}
			public void setInvestInterest(double investInterest) {
				this.investInterest = investInterest;
			}
			public double getRateInterest() {
				return rateInterest;
			}
			public void setRateInterest(double rateInterest) {
				this.rateInterest = rateInterest;
			}
			public double getInvestmentIncentive() {
				return investmentIncentive;
			}
			public void setInvestmentIncentive(double investmentIncentive) {
				this.investmentIncentive = investmentIncentive;
			}
			public double getRedEnvelope() {
				return redEnvelope;
			}
			public void setRedEnvelope(double redEnvelope) {
				this.redEnvelope = redEnvelope;
			}
			public double getRecommendedAwards() {
				return recommendedAwards;
			}
			public void setRecommendedAwards(double recommendedAwards) {
				this.recommendedAwards = recommendedAwards;
			}
		}

		public Accountinfovo getAccountinfovo() {
			return accountinfovo;
		}

		public void setAccountinfovo(Accountinfovo accountinfovo) {
			this.accountinfovo = accountinfovo;
		}
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
