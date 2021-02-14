import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject var viewModel = TrendingViewModel()

    var body: some View {
        let repositories = viewModel.repositories ?? Array()

        List(repositories, id: \.self) { repository in
            Text(repository.name)
        }.onAppear {
            viewModel.fetch()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
