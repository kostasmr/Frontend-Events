import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend_events.R
import com.example.frontend_events.models.TicketOrder
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

class TicketAdapter(private val tickets: List<TicketOrder>) :
    RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    class TicketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.titleTextView)
        val locationText: TextView = view.findViewById(R.id.locationTextView)
        val priceText: TextView = view.findViewById(R.id.priceTextView)
        val nameText: TextView = view.findViewById(R.id.nameTextView)
        val quantityText: TextView = view.findViewById(R.id.quantityTextView)
        val totalText: TextView = view.findViewById(R.id.totalTextView)
        val qrCodeImage: ImageView = view.findViewById(R.id.qrCodeImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.titleText.text = ticket.title
        holder.locationText.text = ticket.location
        holder.priceText.text = ticket.price
        holder.nameText.text = "Name: ${ticket.purchaserName}"
        holder.quantityText.text = "Tickets: ${ticket.numberOfTickets}"
        holder.totalText.text = "Total Paid: ${ticket.price}"

        val qrBitmap = generateQRCode(ticket.qrCode)
        holder.qrCodeImage.setImageBitmap(qrBitmap)
    }

    override fun getItemCount(): Int = tickets.size

    private fun generateQRCode(text: String): Bitmap {
        val size = 256
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)

        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }

        return bitmap
    }
}
