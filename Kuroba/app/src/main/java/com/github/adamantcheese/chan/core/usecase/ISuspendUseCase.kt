package com.github.adamantcheese.chan.core.usecase

interface ISuspendUseCase<Parameter, Result> {
  suspend fun execute(parameter: Parameter): Result
}