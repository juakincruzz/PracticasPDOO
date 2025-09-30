# frozen_string_literal: true
module Irrgarten
  class Shield
    def initialize(protection, uses)
      @protection = protection.to_f
      @uses = uses.to_i
    end

    def protect()
      return 0.0 if @uses <= 0
      @uses -= 1
      @protection
    end

    def to_s
      return "S[" + @protection.to_s + ", " + @uses.to_s + "]"
    end

    def discard
      Dice.discard_element(@uses)
    end

    protected
    attr_reader :protection, :uses
  end
end
