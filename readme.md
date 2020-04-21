
## 1: Design Pattern Ideas
**Control Layer**
I'm thinking of using a series of singleton control classes to make a control class layer that keeps the interface away from the entities.

**Types of Farms, Farmers etc:**
An abstract factory might be a good approach to creating different types of farms, farmers, and other dynamic objects. I'll have to try it before I'll really know.

**Behavior Based on States**
Livestock, crops, farms, etc, behave differently depending on their states, which depends on how many cycles have passed. I will probably either use a State Pattern, or a Strategy Pattern. I will play around with both before I decide.